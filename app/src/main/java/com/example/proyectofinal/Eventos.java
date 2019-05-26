package com.example.proyectofinal;



import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.lang.Object;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.List;

import harmony.java.awt.Color;

public class Eventos extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    int idEvento;
    String uidUser, ClaveVoto;
    ImageView img1,img2;
    private final static String NOMBRE_DIRECTORIO = "MiPdf";

    private final static String ETIQUETA_ERROR = "ERROR";
    List<String> nomLocal;
    List<String> nomVisitante;
    List<String> link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        Bundle parametros = getIntent().getExtras();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uidUser = user.getUid();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        }
        if (parametros != null) {
        idEvento=Integer.parseInt(parametros.getString("idPartido"));

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            link = databaseAccess.getLink(idEvento);
            List<String> local = databaseAccess.getLocal(idEvento);
            List<String> visitante = databaseAccess.getVisitante(idEvento);
            List<String> imgLocal = databaseAccess.getIcono(Integer.parseInt(local.get(0)));
            List<String> imgVisitante = databaseAccess.getIcono(Integer.parseInt(visitante.get(0)));
            nomLocal = databaseAccess.getNombre(Integer.parseInt(local.get(0)));
            nomVisitante = databaseAccess.getNombre(Integer.parseInt(visitante.get(0)));

            String foto1 = imgLocal.get(0);
            String iLocal = foto1.substring(0, foto1.length()-4);
            int id11 = this.getResources().getIdentifier(iLocal, "mipmap", getPackageName());

            img1.setImageResource(id11);
            String foto2 = imgVisitante.get(0);
            String iVisitante = foto2.substring(0, foto2.length()-4);
            int id12 = this.getResources().getIdentifier(iVisitante, "mipmap", getPackageName());

            img2.setImageResource(id12);

        }
        ClaveVoto=uidUser+idEvento;



    }
    public void btnVer_clicked(View v){

        Uri uri = Uri.parse(link.get(0));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void btnLocal_clicked(View v){
        Votos voto = new Votos(uidUser, idEvento, 1);
        mDatabase.child("votos").child(ClaveVoto).setValue(voto);
        Button btn1=(Button)findViewById(R.id.btnLocal);
        Button btn2=(Button)findViewById(R.id.btnVisitante);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);

        generarPDF(nomLocal.get(0));

    }
    public void btnVisitante_clicked(View v){
       Votos voto = new Votos(uidUser, idEvento, 2);
        mDatabase.child("votos").child(ClaveVoto).setValue(voto);
        generarPDF(nomVisitante.get(0));
    }
    public void generarPDF(String votado){


            // Creamos el documento.


            Document documento = new Document();





            try {

                File f = crearFichero(ClaveVoto+".pdf");

                // Creamos el flujo de datos de salida para el fichero donde
                // guardaremos el pdf.
                FileOutputStream ficheroPdf = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+ClaveVoto+".pdf");

                // Asociamos el flujo que acabamos de crear al documento.
                PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

                // Incluimos el pie de pagina y una cabecera
                HeaderFooter cabecera = new HeaderFooter(new Phrase(
                        "Evento"), false);
                HeaderFooter pie = new HeaderFooter(new Phrase(
                        "Proyecto Final"), false);

                documento.setHeader(cabecera);
                documento.setFooter(pie);

                // Abrimos el documento.
                documento.open();

                // Añadimos un titulo con la fuente por defecto.
                documento.add(new Paragraph("En el Evento "+nomLocal.get(0)+" VS "+nomVisitante.get(0)));

                Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                        Font.BOLD, Color.black);
                documento.add(new Paragraph("Realizastes el voto por " +votado, font));

                // Insertamos una imagen que se encuentra en los recursos de la
                // aplicacion.
                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.banner);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                Image imagen = Image.getInstance(stream.toByteArray());

                documento.add(imagen);

                Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.qr);
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                Image imagen1 = Image.getInstance(stream1.toByteArray());

                documento.add(imagen1);



            } catch (DocumentException e) {

                Log.e(ETIQUETA_ERROR, e.getMessage());

            } catch (IOException e) {

                Log.e(ETIQUETA_ERROR, e.getMessage());

            } finally {
                // Cerramos el documento.
                documento.close();
            }
        }


        public static File crearFichero(String nombreFichero) throws IOException {
            File ruta = getRuta();
            File fichero = null;
            if (ruta != null)
                fichero = new File(ruta, nombreFichero);
            return fichero;
        }

        /**
         * Obtenemos la ruta donde vamos a almacenar el fichero.
         *
         * @return
         */
        public static File getRuta() {

            // El fichero sera almacenado en un directorio dentro del directorio
            // Descargas
            File ruta = null;
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {
                ruta = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        NOMBRE_DIRECTORIO);

                if (ruta != null) {
                    if (!ruta.mkdirs()) {
                        if (!ruta.exists()) {
                            return null;
                        }
                    }
                }
            } else {
            }

            return ruta;
        }
    }



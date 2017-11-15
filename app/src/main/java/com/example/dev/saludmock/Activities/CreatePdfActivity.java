package com.example.dev.saludmock.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.dev.saludmock.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dev on 15/08/17.
 */

public class CreatePdfActivity extends Activity {

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pdf);
        askPermissionAndWriteFile();


    }
    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (canWrite && canRead) {
            this.writeFile();

        }
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        writeFile();
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }


    private void writeFile() {

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


         final Font BLACK_BOLD1 = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
         final Font BLACK_BOLD2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
         final Font BLACK_BOLD3 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

         final Chunk A = new Chunk("ABOGADOS DE LOS ANIMALES, A.C.", BLACK_BOLD1);
         final Chunk B = new Chunk("CAMPAÑA DE ESTERILIZACIÓN", BLACK_BOLD2);
         final Chunk C = new Chunk("SOLICITUD Y RESPONSIVA" + "Ciudad: Querétaro " +  "Fecha: ", BLACK_BOLD2);
         final Chunk D = new Chunk("ABOGADOS DE LOS ANIMALES, A.C.", BLACK_BOLD3);
         final Chunk E = new Chunk("El curso post operatorio es responsabilidad del propietario de la mascota esterilizada y deberá ser supervisada por un médico veterinario competente, siguiendo las recomendaciones del médico veterinario que efectúe la cirugía. \n \n", BLACK_BOLD3);
         final Chunk F = new Chunk("REQUISITOS: \n ", BLACK_BOLD3);


        String nombre = getIntent().getExtras().getString("nombre");
        String numero = getIntent().getExtras().getString("numero");
        String mascota = getIntent().getExtras().getString("mascota");
        String telefono = getIntent().getExtras().getString("telefono");
        String direccion = getIntent().getExtras().getString("direccion");
        String edad = getIntent().getExtras().getString("edad");
        String raza = getIntent().getExtras().getString("raza");


        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;

            //create document object
            Document doc = new Document();
            //output file path

            try {
                String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ Cartas Responsivas";

                File dir = new File(file);
                if(!dir.exists())
                    dir.mkdirs();
                Log.d("PDFCreator", "PDF Path:" + file);

                File nfile = new File(dir, nombre + ".pdf");
                FileOutputStream fOut = new FileOutputStream(nfile);
                PdfWriter.getInstance(doc, fOut);
                //create pdf writer instance
               // PdfWriter.getInstance(doc, new FileOutputStream(file));
                //open the document for writing
                doc.open();

                Drawable d = getResources().getDrawable(R.drawable.logo);

                BitmapDrawable bitDw = ((BitmapDrawable) d);

                Bitmap bmp = bitDw.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                Image image = Image.getInstance(stream.toByteArray());

                image.scaleAbsolute(50f, 50f);

                doc.add(image);

                doc.add(new Paragraph(A));
                doc.add(new Paragraph(B));

                LineSeparator ls = new LineSeparator();
                doc.add(new Chunk(ls));

                //add paragraph to the document
                doc.add(new Paragraph(C + dateFormat.format(date)));

                doc.add(new Paragraph(D + "no se hace responsable por muerte, pérdida o algún otro contratiempo que ocurra durante la cirugía o post operatorio. \n"));

                doc.add(new Paragraph(E));

                doc.add(new Paragraph(F));

                doc.add(new Paragraph("1. La persona que presente la mascota para su esterilización debe de ser mayor de edad. \n " +
                        "2. La mascota deberá tener por lo menos 2 meses de edad o 1 kg de peso. \n" +
                        "3. La mascota deberá estar sin comer ni beber agua por lo menos 12 horas antes de la cirugía. De lo contrario \n" +
                        "4. La mascota NO deberá estar lactando, gestando o en celo. \n" +
                        "5. La mascota deberá estar sana y o más limpa posible.\n" +
                        "6. Acepta que se marque la oreja de su mascota con un pequeño tatuaje. \n" +
                        "7. RESCATADOS: " + "Más de 25 días de haberlos rescatados. \n " +
                        "8. No se le debe de haber aplicado ninguna vacuna 2 semanas antes de la cirugía ni deben de aplicarse vacunas \n" +
                        "mínimo en los siguientes 20 días después de la cirugía."));

                doc.add(new Paragraph("DATOS DEL PROPIETARIO: \n"));

                doc.add(new Paragraph("Nombre: " + nombre));
                doc.add(new Paragraph("Dirección: " + direccion));
                doc.add(new Paragraph("Cel / Tel: " + telefono));

                doc.add(new Paragraph("DATOS DE LA MASCOTA: \n"));

                doc.add(new Paragraph("Nombre: " + mascota));
                doc.add(new Paragraph("Edad: " + edad));
                doc.add(new Paragraph("Raza: " + raza));
                doc.add(new Paragraph("Color:___________________ " + "¿Tiene vacuna antirrabica?    Si       No   "));
                doc.add(new Paragraph("Tamaño:     Ch     Med     Gde    Gigante     Peso:__________________(nosotros l@s pesamos)"));
                doc.add(new Paragraph("OBSERVACIONES(condición médica, alergías, algunos problemas con el uso de anestesia)_________" +
                        "___________________________________________________________________________________________________________ \n\n\n"));


                doc.add(new Paragraph("Acepto las condiciones y confirmo que recibí el volante de cuidados post operatorio: \n"));


                doc.add(new Paragraph("Nombre y Firma:"));

                //close the document
                doc.close();

                Toast.makeText(getApplicationContext(), "Su PDF ha sido creado y guardado en la memoria externa. ", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

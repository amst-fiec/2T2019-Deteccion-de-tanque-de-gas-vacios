package Objetos;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tweet {
    private String autor;
    private String tweet;
    private String fecha;

         public Tweet(String autor, String tweet){
            this.autor = autor;
            this.tweet = tweet;
         }
        public void publicarTweet(){
         String fecha_actual = new SimpleDateFormat("dd-MMyyyy", Locale.getDefault()).format(new Date());
         this.fecha = fecha_actual;
         }
}


import java.io.IOException;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.sql.*;

public class Principale {
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   
	
	
	public static void main(String[] args) throws Exception {
		Document doc;
		   Connection conn = null;
		   Statement stmt = null;
		try {
			
		     //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);


		      stmt = conn.createStatement();
		  
		      System.out.println("Database created successfully...");
		   }catch(SQLException se){
		      //Handle errors for JDBC
		     // se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		     // e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		     /* try{
		         if(stmt!=null)
		             stmt.close(); 
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		             conn.close(); 
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try*/
		        int pos=1;
		        int idprodotto=0, pezziscatola=0, larghezza=0, profondita=0, altezza=0;
		        String  descrizionearticolo="", datascadenza="", descrizionedettagliata="", brand="", unitamisura="";
	        	double peso=0, prezzo=0;
	        	long codicebarre=0;
			
	        	Elements links,media, imports, item,  scheda, newsHeadlines;
		
				   int index=2293;
				   for (int i=0;i<19000;i++){
			doc = Jsoup.connect("https://www.spesasicura.com/product/details/"+ index).get();
			//newsHeadlines = doc.select("#mp-itn b a");
		    
	        //links = doc.select("a[href]");
	        //media = doc.select("[src]");
	        //imports = doc.select("link[href]");
	        item = doc.select("span[itemprop=price]");
	        scheda = doc.select("td");
        	descrizionearticolo=datascadenza=descrizionedettagliata=brand=unitamisura="";
        	idprodotto=pezziscatola=larghezza=profondita=altezza=0;
        	peso=prezzo=codicebarre=0;
	        Elements suc ;
			
	        pos=1;
	        
	        for(org.jsoup.nodes.Element l : scheda){
        		suc=l.select("td[class=tdSched]");
        		if(suc.text().length( ) >0 && suc.text().length( )<300 && scheda.get(pos).text().length()>0){
        			
        		switch(scheda.get(pos-1).text()){
        			case "Descrizione Articolo": descrizionearticolo=scheda.get(pos).text().replaceAll("'","''");
        		break;        			
        			case "Id Prodotto": idprodotto=Integer.valueOf(scheda.get(pos).text());
        		break;
        			case "Codice a barre": codicebarre=Long.valueOf(scheda.get(pos).text());
       			 break;
        			case "Data Scadenza": datascadenza=scheda.get(pos).text();
       			 break;
        			case "Descrizione Dettagliata": descrizionedettagliata=scheda.get(pos).text().replaceAll("'","''");
       			 break;
        			case "Brand": brand=scheda.get(pos).text().replaceAll("'","''");;
       			 break;
        			case "Unità di misura": unitamisura=scheda.get(pos).text();
       			 break;
        			case "Pezzi Per Scatola": pezziscatola=Integer.valueOf(scheda.get(pos).text());
       			 break;
        			case "Larghezza": larghezza=Integer.valueOf(scheda.get(pos).text().replaceAll(" mm","").replace(".",""));
       			 break;
        			case "Profondità": profondita=Integer.valueOf(scheda.get(pos).text().replaceAll(" mm","").replace(".",""));
       			 break;        			
        			case "Altezza": altezza=Integer.valueOf(scheda.get(pos).text().replaceAll(" mm","").replace(".",""));
       			 break;
        			case "Peso in KG": peso=Double.valueOf(scheda.get(pos).text().replaceAll(",","."));
       			 break;
        			case "Parole chiave (tag):": 
       			 break;
      	        			
        			}
        			//System.out.println(idprodotto + codicebarre + pezziscatola );
        			System.out.println(scheda.get(pos-1).text()+": " +scheda.get(pos).text());	
        		}
				pos++;
        	}
	        
	        	if(descrizionearticolo.length()>3){
	        		prezzo=Double.valueOf(item.get(0).text().replaceAll(",","."));
		      String sql = "INSERT INTO prodotti.prodotto(prezzo, descrizionearticolo, idprodotto, codiceabarre, datascadenza, descrizionedettagliata, brand, unitadimisura, pezziperscatola, larghezza, profondita, altezza, pesoinKG) VALUES("+ prezzo +" ,'"+ descrizionearticolo +"' ,"+idprodotto +" ,"+ codicebarre +" ,'"+ datascadenza +"' ,'"+ descrizionedettagliata +"' ,'"+  brand +"' ,'"+ unitamisura +"' ,"+  pezziscatola +" ,"+  larghezza  +" ,"+ profondita+" ,"+ altezza  +" ,"+ peso+ "); ";
		      stmt.executeUpdate(sql);}
		      System.out.println(index); 
		      index++;
		      
		      
		}
}}
}

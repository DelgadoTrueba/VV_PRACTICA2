/**
 * Auxiliar class for persistence in text files
 * of the class Agenda.
 * 
 * A Parser translates between Entry objects and
 * lines of the text file (String objects).
 * 
 * If a Entry is given via the method insertEntry,
 * the Parser translates it into a line that can
 * be recovered with the getLine method.
 * 
 * Analogously, given a String via the method
 * insertLine, the Parser creates a Entry object
 * with the same data that can be recovered
 * with the getEntry method.
 */
public class Parser {
	final static String NOM = "NOMBRE="; //7
	final static String APE = "APELLIDOS="; //11
	final static String DIR = "DIRECCION=";//11
	final static String POB = "POBLACION=";//11
	final static String PRO = "PROVINCIA=";//11
	final static String COD = "CODIGO=";//7
	final static String TFN = "TELEFONO=";//9
	final static String ANNO = "ANIONACIM=";//10
	
	private String line;
	private Entry en;
	
	public Parser () {
		line = null;
		en = new Entry ();
	}
	
	public void insertEntry (Entry p) {
		en = p;
		line = this.createLine ();
	}

	public void insertLine (String l) {
		line = l;
		en = this.createEntry ();
	}
	
	public Entry getEntry () {
		return en;
	}

	public String getLine () {
		return line;
	}
	
	private Entry createEntry () {
		String nombre = new String ();
		String apellido = new String ();
		String direccion = new String ();
		String poblacion = new String ();
		String provincia = new String ();
		String codigo = new String ();
		String telefono = new String ();
		int anno = 0;
		int posNom,posApe,posDir,posPob, posProv,posCod,posTfno,posAnno,longitud;
		boolean auxb = true;
		Entry auxp = new Entry ();

		longitud = line.length ();
		posNom = line.indexOf (NOM);
		posApe = line.indexOf (APE);
		posDir = line.indexOf (DIR);
		posPob = line.indexOf (POB);
		posProv = line.indexOf (PRO);
		posCod = line.indexOf (COD);
		posTfno = line.indexOf (TFN);
		posAnno = line.indexOf (ANNO);
  
		if ((posNom != -1) && (posApe != -1) && (posDir != -1) && (posPob != -1) && (posCod != -1) && (posTfno != -1) && (posAnno != -1)) {
			nombre = line.substring (posNom+7, posApe-2).trim ();
			apellido = line.substring (posApe+11, posDir-2).trim ();
			direccion = line.substring (posDir+11, posPob-2).trim ();
			poblacion = line.substring (posPob+11, posProv-2).trim ();
			provincia = line.substring (posProv+11, posCod-2).trim ();
			codigo = line.substring (posCod+7, posTfno-2).trim ();
			telefono = line.substring (posTfno+9, posAnno-2).trim ();
			anno = Integer.parseInt (line.substring (posAnno+10,longitud).trim ());
			auxb = true;
		}
        else auxb = false;
		if (auxb) {
			auxp.setName (nombre);
			auxp.setSurname (apellido);
			auxp.setAddress (direccion);
			auxp.setCity (poblacion);
			auxp.setCounty (provincia);
			auxp.setZip (codigo);
			auxp.setTelephone (telefono);
			auxp.setBirthYear (anno);
		}
		return auxp;
	}
	
	private String createLine() {
		String nombre = en. getName ();
		String apellido = en.getSurname ();
		String direccion = en.getAddress ();
		String poblacion = en.getCity ();
		String provincia = en.getCounty ();
		String codigo = en.getZip ();
		String telefono = en.getTelephone ();
		int anno = en.getBirthYear ();
		String aux2 = "";

		if (en.hasData()) {
			aux2 = NOM + " " + nombre + ". " + APE + " " + apellido + ". " + DIR + " " + direccion + ". ";
			aux2 = aux2 + POB + " " + poblacion + ". " + PRO + " " + provincia + ". " + COD + " "  + codigo + ". " ;
			aux2 = aux2 + TFN + " "  + telefono + ". " + ANNO + " " + anno;
		}
		return aux2;
	}

}
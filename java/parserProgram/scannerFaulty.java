/* The following code was generated by JFlex 1.4.3 on 11/21/18 1:18 PM */

package parserProgram;

import java_cup.runtime.*;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 11/21/18 1:18 PM from the specification file
 * <tt>/Users/luciano/Desktop/research/tools/maskD/src/parser/parserFaulty/jflex/scannerFaulty.jflex</tt>
 */
public class scannerFaulty implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = {
     0,  0,  0,  0,  0,  0,  0,  0,  0,  7,  4,  0,  7,  6,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     7, 20,  0,  0,  0,  0, 18,  0, 10, 11,  5,  8, 16,  9, 25,  3, 
     1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 13, 12, 19, 15, 14,  0, 
     0,  2, 34, 53,  2, 43,  2, 40,  2, 37,  2,  2, 36, 52, 38, 35, 
    50,  2,  2,  2, 39,  2,  2,  2,  2,  2,  2, 23,  0, 24,  0,  0, 
     0, 31, 42, 51,  2, 29, 30, 49, 54, 46,  2,  2, 32, 45, 44, 41, 
    48,  2, 27, 33, 26, 28, 47,  2,  2, 55,  2, 21, 17, 22,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
  };

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\5"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\2\1\1\20\1\21\1\22\1\23\1\24\1\25"+
    "\1\26\20\3\1\27\1\0\1\30\1\31\1\32\1\33"+
    "\11\3\1\34\7\3\2\0\1\3\1\35\4\3\1\36"+
    "\5\3\1\37\1\40\3\3\1\27\1\41\1\42\2\3"+
    "\1\43\3\3\1\44\2\3\1\45\2\3\1\46\6\3"+
    "\1\47\2\3\1\50\3\3\1\51\2\3\1\52\1\53"+
    "\1\3\1\54\1\55";

  private static int [] zzUnpackAction() {
    int [] result = new int[121];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\70\0\160\0\250\0\340\0\70\0\70\0\u0118"+
    "\0\70\0\u0150\0\70\0\70\0\70\0\70\0\70\0\u0188"+
    "\0\70\0\u01c0\0\u01f8\0\70\0\70\0\70\0\70\0\70"+
    "\0\70\0\70\0\u0230\0\u0268\0\u02a0\0\u02d8\0\u0310\0\u0348"+
    "\0\u0380\0\u03b8\0\u03f0\0\u0428\0\u0460\0\u0498\0\u04d0\0\u0508"+
    "\0\u0540\0\u0578\0\u05b0\0\u05e8\0\70\0\70\0\70\0\70"+
    "\0\u0620\0\u0658\0\u0690\0\u06c8\0\u0700\0\u0738\0\u0770\0\u07a8"+
    "\0\u07e0\0\250\0\u0818\0\u0850\0\u0888\0\u08c0\0\u08f8\0\u0930"+
    "\0\u0968\0\u09a0\0\u09d8\0\u0a10\0\250\0\u0a48\0\u0a80\0\u0ab8"+
    "\0\u0af0\0\250\0\u0b28\0\u0b60\0\u0b98\0\u0bd0\0\u0c08\0\250"+
    "\0\250\0\u0c40\0\u0c78\0\u0cb0\0\70\0\250\0\250\0\u0ce8"+
    "\0\u0d20\0\250\0\u0d58\0\u0d90\0\u0dc8\0\250\0\u0e00\0\u0e38"+
    "\0\250\0\u0e70\0\u0ea8\0\250\0\u0ee0\0\u0f18\0\u0f50\0\u0f88"+
    "\0\u0fc0\0\u0ff8\0\250\0\u1030\0\u1068\0\250\0\u10a0\0\u10d8"+
    "\0\u1110\0\250\0\u1148\0\u1180\0\250\0\250\0\u11b8\0\250"+
    "\0\250";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[121];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\6"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30"+
    "\1\31\1\32\1\33\1\34\1\35\1\4\1\36\3\4"+
    "\1\37\2\4\1\40\1\41\1\4\1\42\1\43\1\4"+
    "\1\44\2\4\1\45\1\4\1\46\1\47\1\50\1\4"+
    "\1\51\1\52\2\4\71\0\1\3\67\0\2\4\27\0"+
    "\36\4\3\0\1\53\1\0\1\54\66\0\1\6\101\0"+
    "\1\55\70\0\1\56\71\0\1\57\70\0\1\60\46\0"+
    "\2\4\27\0\1\4\1\61\34\4\1\0\2\4\27\0"+
    "\2\4\1\62\33\4\1\0\2\4\27\0\7\4\1\63"+
    "\26\4\1\0\2\4\27\0\5\4\1\64\30\4\1\0"+
    "\2\4\27\0\11\4\1\65\24\4\1\0\2\4\27\0"+
    "\14\4\1\66\5\4\1\67\13\4\1\0\2\4\27\0"+
    "\17\4\1\70\16\4\1\0\2\4\27\0\6\4\1\71"+
    "\27\4\1\0\2\4\27\0\4\4\1\72\31\4\1\0"+
    "\2\4\27\0\22\4\1\73\13\4\1\0\2\4\27\0"+
    "\22\4\1\74\13\4\1\0\2\4\27\0\2\4\1\75"+
    "\33\4\1\0\2\4\27\0\3\4\1\76\32\4\1\0"+
    "\2\4\27\0\1\4\1\77\34\4\1\0\2\4\27\0"+
    "\5\4\1\100\30\4\1\0\2\4\27\0\34\4\1\101"+
    "\1\4\4\53\1\0\63\53\3\54\1\102\1\54\1\103"+
    "\62\54\1\0\2\4\27\0\2\4\1\104\33\4\1\0"+
    "\2\4\27\0\22\4\1\105\13\4\1\0\2\4\27\0"+
    "\3\4\1\106\32\4\1\0\2\4\27\0\2\4\1\107"+
    "\3\4\1\110\27\4\1\0\2\4\27\0\11\4\1\111"+
    "\24\4\1\0\2\4\27\0\15\4\1\112\20\4\1\0"+
    "\2\4\27\0\24\4\1\113\11\4\1\0\2\4\27\0"+
    "\1\4\1\114\34\4\1\0\2\4\27\0\17\4\1\115"+
    "\16\4\1\0\2\4\27\0\2\4\1\116\33\4\1\0"+
    "\2\4\27\0\1\117\35\4\1\0\2\4\27\0\1\120"+
    "\35\4\1\0\2\4\27\0\1\121\35\4\1\0\2\4"+
    "\27\0\17\4\1\122\16\4\1\0\2\4\27\0\24\4"+
    "\1\123\11\4\1\0\2\4\27\0\5\4\1\124\30\4"+
    "\3\54\1\102\1\54\1\0\65\54\1\125\64\54\1\0"+
    "\2\4\27\0\3\4\1\126\32\4\1\0\2\4\27\0"+
    "\7\4\1\127\26\4\1\0\2\4\27\0\6\4\1\130"+
    "\27\4\1\0\2\4\27\0\7\4\1\131\26\4\1\0"+
    "\2\4\27\0\12\4\1\132\23\4\1\0\2\4\27\0"+
    "\1\133\35\4\1\0\2\4\27\0\23\4\1\134\12\4"+
    "\1\0\2\4\27\0\20\4\1\135\15\4\1\0\2\4"+
    "\27\0\23\4\1\136\12\4\1\0\2\4\27\0\3\4"+
    "\1\137\32\4\1\0\2\4\27\0\31\4\1\140\4\4"+
    "\1\0\2\4\27\0\22\4\1\141\13\4\1\0\2\4"+
    "\27\0\22\4\1\142\13\4\1\0\2\4\27\0\1\143"+
    "\35\4\1\0\2\4\27\0\3\4\1\144\32\4\1\0"+
    "\2\4\27\0\24\4\1\145\11\4\1\0\2\4\27\0"+
    "\5\4\1\146\30\4\1\0\2\4\27\0\5\4\1\147"+
    "\30\4\1\0\2\4\27\0\1\4\1\150\34\4\1\0"+
    "\2\4\27\0\3\4\1\151\32\4\1\0\2\4\27\0"+
    "\22\4\1\152\13\4\1\0\2\4\27\0\35\4\1\153"+
    "\1\0\2\4\27\0\5\4\1\154\30\4\1\0\2\4"+
    "\27\0\1\155\35\4\1\0\2\4\27\0\6\4\1\156"+
    "\27\4\1\0\2\4\27\0\22\4\1\157\13\4\1\0"+
    "\2\4\27\0\7\4\1\160\26\4\1\0\2\4\27\0"+
    "\3\4\1\161\32\4\1\0\2\4\27\0\6\4\1\162"+
    "\27\4\1\0\2\4\27\0\24\4\1\163\11\4\1\0"+
    "\2\4\27\0\5\4\1\164\30\4\1\0\2\4\27\0"+
    "\7\4\1\165\26\4\1\0\2\4\27\0\6\4\1\166"+
    "\27\4\1\0\2\4\27\0\25\4\1\167\10\4\1\0"+
    "\2\4\27\0\6\4\1\170\27\4\1\0\2\4\27\0"+
    "\3\4\1\171\32\4";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4592];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\3\1\2\11\1\1\1\11\1\1\5\11"+
    "\1\1\1\11\2\1\7\11\21\1\1\0\4\11\21\1"+
    "\2\0\21\1\1\11\44\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[121];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
	public scannerFaulty(java.io.InputStream r, SymbolFactory sf){
		this(r);
		this.sf=sf;
	}
	private SymbolFactory sf;


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public scannerFaulty(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public scannerFaulty(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 36: 
          { //System.out.println(" ENUM :"+ yytext());
           return new Symbol(symFaulty.ENUM,yyline,yycolumn,yytext());
          }
        case 46: break;
        case 25: 
          { //System.out.println(" IGUAL:"+ yytext());
       return new Symbol(symFaulty.DOUBLE_EQUAL,yyline,yycolumn,yytext());
          }
        case 47: break;
        case 22: 
          { //System.out.println(" PUNTO :"+ yytext());
        return new Symbol(symFaulty.POINT,yyline,yycolumn,yytext());
          }
        case 48: break;
        case 28: 
          { //System.out.println(" OF :"+ yytext());
         return new Symbol(symFaulty.OF,yyline,yycolumn,yytext());
          }
        case 49: break;
        case 44: 
          { //System.out.println("INTERNAL :  "+ yytext());
         return new Symbol(symFaulty.INTERNAL,yyline,yycolumn,yytext());
          }
        case 50: break;
        case 31: 
          { //System.out.println(" PUT :"+ yytext());
          return new Symbol(symFaulty.PUT,yyline,yycolumn,yytext());
          }
        case 51: break;
        case 4: 
          { //System.out.println("DIV:"+ yytext());
        return new Symbol(symFaulty.SLASH,yyline,yycolumn,yytext());
          }
        case 52: break;
        case 41: 
          { //System.out.println(" INIT:"+ yytext());
            return new Symbol(symFaulty.INIT,yyline,yycolumn,yytext());
          }
        case 53: break;
        case 8: 
          { //System.out.println("RESTA:"+ yytext()); 
        return new Symbol(symFaulty.MINUS,yyline,yycolumn, yytext());
          }
        case 54: break;
        case 16: 
          { //System.out.println(" MENOR :"+ yytext());
        return new Symbol(symFaulty.LT,yyline,yycolumn,yytext());
          }
        case 55: break;
        case 40: 
          { //System.out.println(" GLOBAL :"+ yytext());
           return new Symbol(symFaulty.GLOBAL,yyline,yycolumn,yytext());
          }
        case 56: break;
        case 45: 
          { //System.out.println(" NORMATIVE:"+ yytext());
              return new Symbol(symFaulty.NORMATIVE,yyline,yycolumn,yytext());
          }
        case 57: break;
        case 29: 
          { //System.out.println(" RUN:"+ yytext());
          return new Symbol(symFaulty.RUN,yyline,yycolumn,yytext());
          }
        case 58: break;
        case 43: 
          { //System.out.println(" CHANNEL:"+ yytext()); 
             return new Symbol(symFaulty.CHANNEL,yyline,yycolumn,yytext());
          }
        case 59: break;
        case 11: 
          { //System.out.println("PUNTOYCOMA: "+ yytext());
        return new Symbol(symFaulty.SEMICOLON,yyline,yycolumn,yytext());
          }
        case 60: break;
        case 13: 
          { //System.out.println("MAYOR :"+ yytext());
        return new Symbol(symFaulty.GT,yyline,yycolumn,yytext());
          }
        case 61: break;
        case 19: 
          { //System.out.println(" LLAVECIERRA:"+ yytext());
        return new Symbol(symFaulty.RBRACE,yyline,yycolumn,yytext());
          }
        case 62: break;
        case 5: 
          { /* Ignore */
          }
        case 63: break;
        case 38: 
          { //System.out.println(" FALSE :"+ yytext());
           return new Symbol(symFaulty.FALSE,yyline,yycolumn, yytext());
          }
        case 64: break;
        case 20: 
          { //System.out.println(" CORCHETE ABRE:"+ yytext());
        return new Symbol(symFaulty.LBRACKET,yyline,yycolumn,yytext());
          }
        case 65: break;
        case 10: 
          { //System.out.println("RPARENT:"+ yytext());
        return new Symbol(symFaulty.RPARENT,yyline,yycolumn,yytext());
          }
        case 66: break;
        case 7: 
          { //System.out.println("SUMA:"+ yytext()); 
        return new Symbol(symFaulty.PLUS,yyline,yycolumn,yytext());
          }
        case 67: break;
        case 24: 
          { //System.out.println("FLECHA: "+ yytext());
        return new Symbol(symFaulty.RIGHTARROW,yyline,yycolumn,yytext());
          }
        case 68: break;
        case 30: 
          { //System.out.println(" INT:"+ yytext());
         return new Symbol(symFaulty.INT,yyline,yycolumn,yytext());
          }
        case 69: break;
        case 6: 
          { //System.out.println("PROD:"+ yytext());
        return new Symbol(symFaulty.ASTERISK,yyline,yycolumn,yytext());
          }
        case 70: break;
        case 18: 
          { //System.out.println(" LLAVEABRE:"+ yytext());
        return new Symbol(symFaulty.LBRACE,yyline,yycolumn,yytext());
          }
        case 71: break;
        case 35: 
          { //System.out.println(" BOOL:"+ yytext());
           return new Symbol(symFaulty.BOOL,yyline,yycolumn,yytext());
          }
        case 72: break;
        case 33: 
          { //System.out.println(" TRUE :"+ yytext()); 
           return new Symbol(symFaulty.TRUE,yyline,yycolumn, yytext());
          }
        case 73: break;
        case 15: 
          { //System.out.println(" COMA:"+ yytext());
        return new Symbol(symFaulty.COMMA,yyline,yycolumn,yytext());
          }
        case 74: break;
        case 37: 
          { //System.out.println(" MAIN :"+ yytext());
          return new Symbol(symFaulty.MAIN,yyline,yycolumn,yytext());
          }
        case 75: break;
        case 1: 
          { //return new Symbol(symFaulty.LEXICAL_ERROR,yyline,yycolumn,yytext());
          }
        case 76: break;
        case 34: 
          { //System.out.println(" USES:"+ yytext());
           return new Symbol(symFaulty.USES,yyline,yycolumn,yytext());
          }
        case 77: break;
        case 12: 
          { //System.out.println(" DOSPUNTOS: "+ yytext());
        return new Symbol(symFaulty.COLON,yyline,yycolumn,yytext());
          }
        case 78: break;
        case 2: 
          { //System.out.println(" VALORINT:"+ yytext());
            Integer value = new Integer(yytext());
            return new Symbol(symFaulty.INTEGER,yyline,yycolumn,value);
          }
        case 79: break;
        case 3: 
          { //System.out.println("ID :  "+ yytext());
         return new Symbol(symFaulty.ID,yyline,yycolumn,yytext());
          }
        case 80: break;
        case 27: 
          { //System.out.println(" AND:"+ yytext());
        return new Symbol(symFaulty.AND,yyline,yycolumn,yytext());
          }
        case 81: break;
        case 17: 
          { //System.out.println(" NEG:"+ yytext());
        return new Symbol(symFaulty.EXCLAMATION,yyline,yycolumn,yytext());
          }
        case 82: break;
        case 21: 
          { //System.out.println(" CORCHETE CIERRA:"+ yytext());
        return new Symbol(symFaulty.RBRACKET,yyline,yycolumn,yytext());
          }
        case 83: break;
        case 32: 
          { //System.out.println(" GET:"+ yytext());
           return new Symbol(symFaulty.GET,yyline,yycolumn,yytext());
          }
        case 84: break;
        case 9: 
          { //System.out.println("LPARENT:"+ yytext());
        return new Symbol(symFaulty.LPARENT,yyline,yycolumn,yytext());
          }
        case 85: break;
        case 42: 
          { //System.out.println(" PROCESS :"+ yytext());
            return new Symbol(symFaulty.PROCESS,yyline,yycolumn,yytext());
          }
        case 86: break;
        case 39: 
          { //System.out.println("FAULTY :  "+ yytext());
         return new Symbol(symFaulty.FAULTY,yyline,yycolumn,yytext());
          }
        case 87: break;
        case 26: 
          { //System.out.println(" OR:"+ yytext());
        return new Symbol(symFaulty.OR,yyline,yycolumn,yytext());
          }
        case 88: break;
        case 23: 
          { 
          }
        case 89: break;
        case 14: 
          { //System.out.println(" ASIG:"+ yytext());
        return new Symbol(symFaulty.EQUAL,yyline,yycolumn,yytext());
          }
        case 90: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return  new Symbol(symFaulty.EOF,yyline,yycolumn,"");
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}

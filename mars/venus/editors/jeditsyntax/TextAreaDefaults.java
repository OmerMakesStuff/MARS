/*
 * TextAreaDefaults.java - Encapsulates default values for various settings
 * Copyright (C) 1999 Slava Pestov
 *
 * You may use and modify this package for any purpose. Redistribution is
 * permitted, in both source and binary form, provided that this notice
 * remains intact in all source distributions of this package.
 */

   package mars.venus.editors.jeditsyntax;

   import mars.Settings;
   import javax.swing.JPopupMenu;
   import javax.swing.UIManager;
   import java.awt.Color;

/**
 * Encapsulates default settings for a text area. This can be passed
 * to the constructor once the necessary fields have been filled out.
 * The advantage of doing this over calling lots of set() methods after
 * creating the text area is that this method is faster.
 */
    public class TextAreaDefaults
   {
      private static TextAreaDefaults DEFAULTS;
   
      public InputHandler inputHandler;
      public SyntaxDocument document;
      public boolean editable;
   
      public boolean caretVisible;
      public boolean caretBlinks;
      public boolean blockCaret;
      public int caretBlinkRate;
      public int electricScroll;
      public int tabSize;
   
      public int cols;
      public int rows;
      public SyntaxStyle[] styles;
      public Color caretColor;
      public Color selectionColor;
      public Color lineHighlightColor;
      public boolean lineHighlight;
      public Color bracketHighlightColor;
      public boolean bracketHighlight;
      public Color eolMarkerColor;
      public boolean eolMarkers;
      public boolean paintInvalid;
   
      public JPopupMenu popup;
   
   /**
    * Returns a new TextAreaDefaults object with the default values filled
    * in.
    */
       public static TextAreaDefaults getDefaults()
      {
         DEFAULTS = new TextAreaDefaults();
      
         DEFAULTS.inputHandler = new DefaultInputHandler();
         DEFAULTS.inputHandler.addDefaultKeyBindings();
         DEFAULTS.editable = true;
      
         DEFAULTS.blockCaret = false;
         DEFAULTS.caretVisible = true;
         DEFAULTS.caretBlinks = (mars.Globals.getSettings().getCaretBlinkRate() != 0);
         DEFAULTS.caretBlinkRate = mars.Globals.getSettings().getCaretBlinkRate();
         DEFAULTS.tabSize = mars.Globals.getSettings().getEditorTabSize();
         DEFAULTS.electricScroll = 0;// was 3.  Will begin scrolling when cursor is this many lines from the edge.
      
         DEFAULTS.cols = 80;
         DEFAULTS.rows = 25;
         DEFAULTS.styles = SyntaxUtilities.getCurrentSyntaxStyles(); // was getDefaultSyntaxStyles()
         DEFAULTS.caretColor = UIManager.getColor("TextField.foreground");
         Color selColor = UIManager.getColor("TextField.selectionBackground");
         DEFAULTS.selectionColor = (selColor != null) ? selColor : new Color(0xccccff);
         // Use a slightly brighter version of the text-field BG for the
         // current line highlight
         Color textFieldBg = UIManager.getColor("TextField.background");
         if (textFieldBg != null) {
            int r = Math.min(255, textFieldBg.getRed()   + 18);
            int g = Math.min(255, textFieldBg.getGreen() + 18);
            int b = Math.min(255, textFieldBg.getBlue()  + 18);
            DEFAULTS.lineHighlightColor = new Color(r, g, b);
         } else {
            DEFAULTS.lineHighlightColor = new Color(0xeeeeee);
         }
         DEFAULTS.lineHighlight = mars.Globals.getSettings().getBooleanSetting(Settings.EDITOR_CURRENT_LINE_HIGHLIGHTING);
         DEFAULTS.bracketHighlightColor = UIManager.getColor("TextField.foreground");
         DEFAULTS.bracketHighlight = false; // assembly language doesn't need this.
         DEFAULTS.eolMarkerColor = new Color(0x009999);
         DEFAULTS.eolMarkers = false; // true;
         DEFAULTS.paintInvalid = false; //true;
         DEFAULTS.document = new SyntaxDocument();
         return DEFAULTS;
      }
   }

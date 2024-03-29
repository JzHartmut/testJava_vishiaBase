package org.vishia.xmlReader.test.genChgSrc;

import java.util.ArrayList;
import java.util.List;

/**This file is generated by genJavaOut.jzTc script. */
public class Office_odtData {

    
    protected Office_document_content office_document_content;
    
    
    
    
    /**Access to parse result.*/
    public Office_document_content get_office_document_content() { return office_document_content; }
    
    



  /**Class for Component Office_document_content. */
  public static class Office_document_content {
  
    
    protected String office_version;
    
    
    
    protected Office_automatic_styles office_automatic_styles;
    
    
    
    protected Office_body office_body;
    
    
    
    protected Office_font_face_decls office_font_face_decls;
    
    
    
    protected String office_scripts;
    
    
    
    
    /**Access to parse result.*/
    public String get_office_version() { return office_version; }
    
    
    
    
    /**Access to parse result.*/
    public Office_automatic_styles get_office_automatic_styles() { return office_automatic_styles; }
    
    
    
    
    /**Access to parse result.*/
    public Office_body get_office_body() { return office_body; }
    
    
    
    
    /**Access to parse result.*/
    public Office_font_face_decls get_office_font_face_decls() { return office_font_face_decls; }
    
    
    
    
    /**Access to parse result.*/
    public String get_office_scripts() { return office_scripts; }
    
    
  
  }




  /**Class for Component Office_automatic_styles. */
  public static class Office_automatic_styles {
  
    
    protected List<Style_style> style_style;
    
    
    
    
    /**Access to parse result, get the elements of the container style_style*/
    public Iterable<Style_style> get_style_style() { return style_style; }
    
    /**Access to parse result, get the size of the container style_style.*/
    public int getSize_style_style() { return style_style.size(); }
    
    
  
  }




  /**Class for Component Office_body. */
  public static class Office_body {
  
    
    protected Office_text office_text;
    
    
    
    
    /**Access to parse result.*/
    public Office_text get_office_text() { return office_text; }
    
    
  
  }




  /**Class for Component Office_font_face_decls. */
  public static class Office_font_face_decls {
  
    
    protected List<Style_font_face> style_font_face;
    
    
    
    
    /**Access to parse result, get the elements of the container style_font_face*/
    public Iterable<Style_font_face> get_style_font_face() { return style_font_face; }
    
    /**Access to parse result, get the size of the container style_font_face.*/
    public int getSize_style_font_face() { return style_font_face.size(); }
    
    
  
  }




  /**Class for Component Style_style. */
  public static class Style_style {
  
    
    protected String style_family;
    
    
    
    protected String style_list_style_name;
    
    
    
    protected String style_master_page_name;
    
    
    
    protected String style_name;
    
    
    
    protected String style_parent_style_name;
    
    
    
    protected String loext_graphic_properties;
    
    
    
    protected Style_paragraph_properties style_paragraph_properties;
    
    
    
    protected Style_section_properties style_section_properties;
    
    
    
    protected Style_text_properties style_text_properties;
    
    
    
    
    /**Access to parse result.*/
    public String get_style_family() { return style_family; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_list_style_name() { return style_list_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_master_page_name() { return style_master_page_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_name() { return style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_parent_style_name() { return style_parent_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_loext_graphic_properties() { return loext_graphic_properties; }
    
    
    
    
    /**Access to parse result.*/
    public Style_paragraph_properties get_style_paragraph_properties() { return style_paragraph_properties; }
    
    
    
    
    /**Access to parse result.*/
    public Style_section_properties get_style_section_properties() { return style_section_properties; }
    
    
    
    
    /**Access to parse result.*/
    public Style_text_properties get_style_text_properties() { return style_text_properties; }
    
    
  
  }




  /**Class for Component Office_text. */
  public static class Office_text {
  
    
    protected String text_use_soft_page_breaks;
    
    
    
    protected Office_forms office_forms;
    
    
    
    protected List<Text_h> text_h;
    
    
    
    protected List<Text_p> text_p;
    
    
    
    protected Text_sequence_decls text_sequence_decls;
    
    
    
    protected Text_table_of_content text_table_of_content;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_use_soft_page_breaks() { return text_use_soft_page_breaks; }
    
    
    
    
    /**Access to parse result.*/
    public Office_forms get_office_forms() { return office_forms; }
    
    
    
    
    /**Access to parse result, get the elements of the container text_h*/
    public Iterable<Text_h> get_text_h() { return text_h; }
    
    /**Access to parse result, get the size of the container text_h.*/
    public int getSize_text_h() { return text_h.size(); }
    
    
    
    
    /**Access to parse result, get the elements of the container text_p*/
    public Iterable<Text_p> get_text_p() { return text_p; }
    
    /**Access to parse result, get the size of the container text_p.*/
    public int getSize_text_p() { return text_p.size(); }
    
    
    
    
    /**Access to parse result.*/
    public Text_sequence_decls get_text_sequence_decls() { return text_sequence_decls; }
    
    
    
    
    /**Access to parse result.*/
    public Text_table_of_content get_text_table_of_content() { return text_table_of_content; }
    
    
  
  }




  /**Class for Component Style_font_face. */
  public static class Style_font_face {
  
    
    protected String style_font_adornments;
    
    
    
    protected String style_font_family_generic;
    
    
    
    protected String style_font_pitch;
    
    
    
    protected String style_name;
    
    
    
    protected String svg_font_family;
    
    
    
    
    /**Access to parse result.*/
    public String get_style_font_adornments() { return style_font_adornments; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_font_family_generic() { return style_font_family_generic; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_font_pitch() { return style_font_pitch; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_name() { return style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_svg_font_family() { return svg_font_family; }
    
    
  
  }




  /**Class for Component Style_paragraph_properties. */
  public static class Style_paragraph_properties {
  
    
    protected String fo_background_color;
    
    
    
    protected String fo_break_before;
    
    
    
    protected String fo_margin_bottom;
    
    
    
    protected String fo_margin_left;
    
    
    
    protected String fo_margin_right;
    
    
    
    protected String fo_margin_top;
    
    
    
    protected String fo_text_align;
    
    
    
    protected String fo_text_indent;
    
    
    
    protected String loext_contextual_spacing;
    
    
    
    protected String style_auto_text_indent;
    
    
    
    protected String style_justify_single_word;
    
    
    
    protected String style_page_number;
    
    
    
    protected String style_writing_mode;
    
    
    
    protected Style_tab_stops style_tab_stops;
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_background_color() { return fo_background_color; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_break_before() { return fo_break_before; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_margin_bottom() { return fo_margin_bottom; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_margin_left() { return fo_margin_left; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_margin_right() { return fo_margin_right; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_margin_top() { return fo_margin_top; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_text_align() { return fo_text_align; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_text_indent() { return fo_text_indent; }
    
    
    
    
    /**Access to parse result.*/
    public String get_loext_contextual_spacing() { return loext_contextual_spacing; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_auto_text_indent() { return style_auto_text_indent; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_justify_single_word() { return style_justify_single_word; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_page_number() { return style_page_number; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_writing_mode() { return style_writing_mode; }
    
    
    
    
    /**Access to parse result.*/
    public Style_tab_stops get_style_tab_stops() { return style_tab_stops; }
    
    
  
  }




  /**Class for Component Style_section_properties. */
  public static class Style_section_properties {
  
    
    protected String style_editable;
    
    
    
    protected Style_columns style_columns;
    
    
    
    
    /**Access to parse result.*/
    public String get_style_editable() { return style_editable; }
    
    
    
    
    /**Access to parse result.*/
    public Style_columns get_style_columns() { return style_columns; }
    
    
  
  }




  /**Class for Component Style_text_properties. */
  public static class Style_text_properties {
  
    
    protected String fo_country;
    
    
    
    protected String fo_font_style;
    
    
    
    protected String fo_font_weight;
    
    
    
    protected String fo_language;
    
    
    
    protected String officeooo_paragraph_rsid;
    
    
    
    protected String officeooo_rsid;
    
    
    
    protected String style_font_style_asian;
    
    
    
    protected String style_font_style_complex;
    
    
    
    protected String style_font_weight_asian;
    
    
    
    protected String style_font_weight_complex;
    
    
    
    protected String style_text_underline_color;
    
    
    
    protected String style_text_underline_style;
    
    
    
    protected String style_text_underline_width;
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_country() { return fo_country; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_font_style() { return fo_font_style; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_font_weight() { return fo_font_weight; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_language() { return fo_language; }
    
    
    
    
    /**Access to parse result.*/
    public String get_officeooo_paragraph_rsid() { return officeooo_paragraph_rsid; }
    
    
    
    
    /**Access to parse result.*/
    public String get_officeooo_rsid() { return officeooo_rsid; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_font_style_asian() { return style_font_style_asian; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_font_style_complex() { return style_font_style_complex; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_font_weight_asian() { return style_font_weight_asian; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_font_weight_complex() { return style_font_weight_complex; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_text_underline_color() { return style_text_underline_color; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_text_underline_style() { return style_text_underline_style; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_text_underline_width() { return style_text_underline_width; }
    
    
  
  }




  /**Class for Component Office_forms. */
  public static class Office_forms {
  
    
    protected String form_apply_design_mode;
    
    
    
    protected String form_automatic_focus;
    
    
    
    
    /**Access to parse result.*/
    public String get_form_apply_design_mode() { return form_apply_design_mode; }
    
    
    
    
    /**Access to parse result.*/
    public String get_form_automatic_focus() { return form_automatic_focus; }
    
    
  
  }




  /**Class for Component Text_h. */
  public static class Text_h {
  
    
    protected String text_outline_level;
    
    
    
    protected String text_style_name;
    
    
    
    protected String text_bookmark_end;
    
    
    
    protected String text_bookmark_start;
    
    
    
    protected Text_span text_span;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_outline_level() { return text_outline_level; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_bookmark_end() { return text_bookmark_end; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_bookmark_start() { return text_bookmark_start; }
    
    
    
    
    /**Access to parse result.*/
    public Text_span get_text_span() { return text_span; }
    
    
  
  }




  /**Class for Component Text_p. */
  public static class Text_p {
  
    
    protected String text_style_name;
    
    
    
    protected Text_a text_a;
    
    
    
    protected List<Text_bookmark_ref> text_bookmark_ref;
    
    
    
    protected List<Text_s> text_s;
    
    
    
    protected List<Text_span> text_span;
    
    
    
    protected String text_tab;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public Text_a get_text_a() { return text_a; }
    
    
    
    
    /**Access to parse result, get the elements of the container text_bookmark_ref*/
    public Iterable<Text_bookmark_ref> get_text_bookmark_ref() { return text_bookmark_ref; }
    
    /**Access to parse result, get the size of the container text_bookmark_ref.*/
    public int getSize_text_bookmark_ref() { return text_bookmark_ref.size(); }
    
    
    
    
    /**Access to parse result, get the elements of the container text_s*/
    public Iterable<Text_s> get_text_s() { return text_s; }
    
    /**Access to parse result, get the size of the container text_s.*/
    public int getSize_text_s() { return text_s.size(); }
    
    
    
    
    /**Access to parse result, get the elements of the container text_span*/
    public Iterable<Text_span> get_text_span() { return text_span; }
    
    /**Access to parse result, get the size of the container text_span.*/
    public int getSize_text_span() { return text_span.size(); }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_tab() { return text_tab; }
    
    
  
  }




  /**Class for Component Text_sequence_decls. */
  public static class Text_sequence_decls {
  
    
    protected List<Text_sequence_decl> text_sequence_decl;
    
    
    
    
    /**Access to parse result, get the elements of the container text_sequence_decl*/
    public Iterable<Text_sequence_decl> get_text_sequence_decl() { return text_sequence_decl; }
    
    /**Access to parse result, get the size of the container text_sequence_decl.*/
    public int getSize_text_sequence_decl() { return text_sequence_decl.size(); }
    
    
  
  }




  /**Class for Component Text_table_of_content. */
  public static class Text_table_of_content {
  
    
    protected String text_name;
    
    
    
    protected String text_protected;
    
    
    
    protected String text_style_name;
    
    
    
    protected Text_index_body text_index_body;
    
    
    
    protected Text_table_of_content_source text_table_of_content_source;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_name() { return text_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_protected() { return text_protected; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public Text_index_body get_text_index_body() { return text_index_body; }
    
    
    
    
    /**Access to parse result.*/
    public Text_table_of_content_source get_text_table_of_content_source() { return text_table_of_content_source; }
    
    
  
  }




  /**Class for Component Style_tab_stops. */
  public static class Style_tab_stops {
  
    
    protected Style_tab_stop style_tab_stop;
    
    
    
    
    /**Access to parse result.*/
    public Style_tab_stop get_style_tab_stop() { return style_tab_stop; }
    
    
  
  }




  /**Class for Component Style_columns. */
  public static class Style_columns {
  
    
    protected String fo_column_count;
    
    
    
    protected String fo_column_gap;
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_column_count() { return fo_column_count; }
    
    
    
    
    /**Access to parse result.*/
    public String get_fo_column_gap() { return fo_column_gap; }
    
    
  
  }




  /**Class for Component Text_span. */
  public static class Text_span {
  
    
    protected String text_style_name;
    
    
    
    protected Text_bookmark_ref text_bookmark_ref;
    
    
    
    protected String text_line_break;
    
    
    
    protected Text_s text_s;
    
    
    
    protected Text_span text_span;
    
    
    
    protected String text_tab;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public Text_bookmark_ref get_text_bookmark_ref() { return text_bookmark_ref; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_line_break() { return text_line_break; }
    
    
    
    
    /**Access to parse result.*/
    public Text_s get_text_s() { return text_s; }
    
    
    
    
    /**Access to parse result.*/
    public Text_span get_text_span() { return text_span; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_tab() { return text_tab; }
    
    
  
  }




  /**Class for Component Text_a. */
  public static class Text_a {
  
    
    protected String text_style_name;
    
    
    
    protected String text_visited_style_name;
    
    
    
    protected String xlink_href;
    
    
    
    protected String xlink_type;
    
    
    
    protected String text_s;
    
    
    
    protected Text_span text_span;
    
    
    
    protected String text_tab;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_visited_style_name() { return text_visited_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_xlink_href() { return xlink_href; }
    
    
    
    
    /**Access to parse result.*/
    public String get_xlink_type() { return xlink_type; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_s() { return text_s; }
    
    
    
    
    /**Access to parse result.*/
    public Text_span get_text_span() { return text_span; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_tab() { return text_tab; }
    
    
  
  }




  /**Class for Component Text_bookmark_ref. */
  public static class Text_bookmark_ref {
  
    
    protected String text_ref_name;
    
    
    
    protected String text_reference_format;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_ref_name() { return text_ref_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_reference_format() { return text_reference_format; }
    
    
  
  }




  /**Class for Component Text_s. */
  public static class Text_s {
  
    
    protected String text_c;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_c() { return text_c; }
    
    
  
  }




  /**Class for Component Text_sequence_decl. */
  public static class Text_sequence_decl {
  
    
    protected String text_display_outline_level;
    
    
    
    protected String text_name;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_display_outline_level() { return text_display_outline_level; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_name() { return text_name; }
    
    
  
  }




  /**Class for Component Text_index_body. */
  public static class Text_index_body {
  
    
    protected Text_index_title text_index_title;
    
    
    
    protected List<Text_p> text_p;
    
    
    
    
    /**Access to parse result.*/
    public Text_index_title get_text_index_title() { return text_index_title; }
    
    
    
    
    /**Access to parse result, get the elements of the container text_p*/
    public Iterable<Text_p> get_text_p() { return text_p; }
    
    /**Access to parse result, get the size of the container text_p.*/
    public int getSize_text_p() { return text_p.size(); }
    
    
  
  }




  /**Class for Component Text_table_of_content_source. */
  public static class Text_table_of_content_source {
  
    
    protected String text_outline_level;
    
    
    
    protected Text_index_title_template text_index_title_template;
    
    
    
    protected List<Text_table_of_content_entry_template> text_table_of_content_entry_template;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_outline_level() { return text_outline_level; }
    
    
    
    
    /**Access to parse result.*/
    public Text_index_title_template get_text_index_title_template() { return text_index_title_template; }
    
    
    
    
    /**Access to parse result, get the elements of the container text_table_of_content_entry_template*/
    public Iterable<Text_table_of_content_entry_template> get_text_table_of_content_entry_template() { return text_table_of_content_entry_template; }
    
    /**Access to parse result, get the size of the container text_table_of_content_entry_template.*/
    public int getSize_text_table_of_content_entry_template() { return text_table_of_content_entry_template.size(); }
    
    
  
  }




  /**Class for Component Style_tab_stop. */
  public static class Style_tab_stop {
  
    
    protected String style_leader_style;
    
    
    
    protected String style_leader_text;
    
    
    
    protected String style_position;
    
    
    
    protected String style_type;
    
    
    
    
    /**Access to parse result.*/
    public String get_style_leader_style() { return style_leader_style; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_leader_text() { return style_leader_text; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_position() { return style_position; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_type() { return style_type; }
    
    
  
  }




  /**Class for Component Text_index_title. */
  public static class Text_index_title {
  
    
    protected String text_name;
    
    
    
    protected String text_style_name;
    
    
    
    protected Text_p text_p;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_name() { return text_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public Text_p get_text_p() { return text_p; }
    
    
  
  }




  /**Class for Component Text_index_title_template. */
  public static class Text_index_title_template {
  
    
    protected String text_style_name;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
  
  }




  /**Class for Component Text_table_of_content_entry_template. */
  public static class Text_table_of_content_entry_template {
  
    
    protected String text_outline_level;
    
    
    
    protected String text_style_name;
    
    
    
    protected String text_index_entry_chapter;
    
    
    
    protected String text_index_entry_link_end;
    
    
    
    protected String text_index_entry_link_start;
    
    
    
    protected String text_index_entry_page_number;
    
    
    
    protected Text_index_entry_tab_stop text_index_entry_tab_stop;
    
    
    
    protected String text_index_entry_text;
    
    
    
    
    /**Access to parse result.*/
    public String get_text_outline_level() { return text_outline_level; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_style_name() { return text_style_name; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_index_entry_chapter() { return text_index_entry_chapter; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_index_entry_link_end() { return text_index_entry_link_end; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_index_entry_link_start() { return text_index_entry_link_start; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_index_entry_page_number() { return text_index_entry_page_number; }
    
    
    
    
    /**Access to parse result.*/
    public Text_index_entry_tab_stop get_text_index_entry_tab_stop() { return text_index_entry_tab_stop; }
    
    
    
    
    /**Access to parse result.*/
    public String get_text_index_entry_text() { return text_index_entry_text; }
    
    
  
  }




  /**Class for Component Text_index_entry_tab_stop. */
  public static class Text_index_entry_tab_stop {
  
    
    protected String style_leader_char;
    
    
    
    protected String style_type;
    
    
    
    
    /**Access to parse result.*/
    public String get_style_leader_char() { return style_leader_char; }
    
    
    
    
    /**Access to parse result.*/
    public String get_style_type() { return style_type; }
    
    
  
  }


}


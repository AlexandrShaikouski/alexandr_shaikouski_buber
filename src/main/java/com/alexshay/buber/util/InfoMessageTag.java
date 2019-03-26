package com.alexshay.buber.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;


public class InfoMessageTag extends BodyTagSupport {
    private static final Logger LOGGER = LogManager.getLogger(InfoMessageTag.class);
    private String message = "";
    public void setMessage (String message) {
        this.message = message;
    }
    @Override
    public int doStartTag() throws JspTagException {
        ResourceBundle localeResourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        try {
            JspWriter out = pageContext.getOut();
            out.write("<div id=\"modalInfoMessage\"  class=\"modal fade\">\n" +
                    "        <div class=\"modal-dialog\">\n" +
                    "            <div class=\"modal-content\">\n" +
                    "                <div class=\"modal-header\"><button class=\"close\" type=\"button\" data-dismiss=\"modal\">x</button>\n" +
                    "                </div>\n" +
                    "                <h3 class=\"modal-title text-center\">" + localeResourceBundle.getString("all.footer.message") + "</h3>\n" +
                    "                <div id=\"infoMessage\" class=\"modal-body text-center\">" + message + "</div>\n" +
                    "                <div class=\"modal-footer\"><button class=\"btn btn-default\" type=\"button\" data-dismiss=\"modal\">" + localeResourceBundle.getString("all.footer.close") + "</button></div>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>");



        } catch (IOException e) {
            LOGGER.error(e);
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</td></tr></tbody></table>");
        } catch (IOException e) {
            LOGGER.error(e);
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}

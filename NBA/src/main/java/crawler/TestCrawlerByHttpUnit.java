package crawler;

import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;



public class TestCrawlerByHttpUnit {
	public static void main(String[] args) throws Exception{
        //创建webclient
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_31);
        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlPage page = null;
        HtmlPage pageResponse = null;

        AjaxController ajaxController = new NicelyResynchronizingAjaxController();

        try
        {
            WebRequest request = new WebRequest(new URL("http://www.baidu.com"));
            webClient.setAjaxController(ajaxController);
            webClient.waitForBackgroundJavaScript(10000);
            page = webClient.getPage(request);
            ajaxController.processSynchron(page, request, true);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        HtmlForm form = page.getFormByName("f");
        HtmlTextInput kw = form.getInputByName("wd");
        kw.setText("小时代");
        HtmlSubmitInput formSubmit = form.getInputByValue("百度一下");
        try
        {
            pageResponse = formSubmit.click();
        }
        catch(IOException e)
        {
            System.out.println("Form Button" + e.getMessage());
        }
        System.out.println(pageResponse.asXml());
    }
}

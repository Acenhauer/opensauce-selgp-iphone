package com.selgp.opensauce.iphone;

import com.selgp.opensauce.BaseSauceLabs;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by guillemhernandezsola on 19/04/14.
 */
public class BasicAlertTest extends BaseSauceLabs {

    @Test
    public void testBasicAlert() throws Exception {
        driver().findElement(By.xpath("//button[2]")).click();

        Alert alert = driver().switchTo().alert();
        //check if title of alert is correct
        Assert.assertEquals(alert.getText(), "Cool title");
        alert.accept();
    }

}

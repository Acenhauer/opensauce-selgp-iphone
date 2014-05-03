package com.selgp.opensauce.iphone;

import com.selgp.opensauce.BaseSauceLabs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by guillemhernandezsola on 19/04/14.
 */
public class SliderTest extends BaseSauceLabs {
    @Test
    public void testSlider() throws Exception {
        //get the slider
        WebElement slider = driver().findElement(By.xpath("//slider[1]"));
        Assert.assertEquals("Slider is not at 50%", slider.getAttribute("value"), "50%");
        TouchActions drag = new TouchActions(driver()).flick(slider, new Integer(-1), 0, 0);
        drag.perform();
        Assert.assertEquals("Slider is not at 0%", slider.getAttribute("value"), "0%");
    }
}

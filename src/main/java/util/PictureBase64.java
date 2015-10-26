package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

class PictureBase64 {

	  public static void main(String[] args) throws UnsupportedEncodingException
	    {
	        String strImg = GetImageStr();
	        System.out.println(strImg);
//	        String strImg1 = "ata%3Aimage%2Fjpeg%3Bbase64%2C%2F9j%2F4AAQSkZJRgABAQAAAQABAAD%2F2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL%2F2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL%2FwAARCACgAIcDASIAAhEBAxEB%2F8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL%2F8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4%2BTl5ufo6erx8vP09fb3%2BPn6%2F8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL%2F8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3%2BPn6%2F9oADAMBAAIRAxEAPwD3nY3%2FAD1f8h%2FhRsb%2FAJ6v%2BQ%2Fwp9FO4rDNjf8APV%2FyH%2BFGxv8Anq%2F5D%2FCn0UXCwzY3%2FPV%2FyH%2BFGxv%2Ber%2FkP8KfRRcLDNjf89X%2FACH%2BFGxv%2Ber%2FAJD%2FAAp9FFwsM2N%2Fz1f8h%2FhRsb%2Fnq%2F5D%2FCn0UXCwzY3%2FAD1f8h%2FhRsb%2FAJ6v%2BQ%2Fwp9FFwsM2N%2Fz1f8h%2FhRsb%2Fnq%2F5D%2FCn0UXCwzY3%2FPV%2FwAh%2FhRsb%2Fnq%2FwCQ%2FwAKfRRcLDNjf89X%2FIf4UU%2Bii4WEY4UmvIx4s8Rt11E%2F9%2BY%2F%2Fia9bk%2F1bfSvJFtOOld%2BBUHzcyT2%2FU5MW5Ll5XYlXxT4gPXUD%2F36T%2F4mpk8T673vj%2F36T%2FCoVtPapFtfau5xpfyr7kcalU%2Fmf3kn%2FCS67%2Fz%2FAB%2F79p%2FhUi%2BJdb73hP8A2zT%2FAAqMWntTxae1Llpfyr7h89T%2BZ%2FeOPiTW%2B14f%2B%2Faf4Uh8R67%2FAM%2Fp%2FwC%2Faf4UotPaj7J7UuWl%2FKvuDmqfzP7xh8R67%2Fz%2FAB%2F79p%2FhTD4k1%2F8A5%2Fj%2FAN%2Bk%2FwAKm%2Bye1N%2By47U%2BWl%2FKvuQc1T%2BZ%2FeR%2F8JJr%2FwDz%2FH%2Fv0n%2BFB8Ta8P8Al%2FP%2FAH6T%2FCnNbe1MNpkdKOWl%2FKvuQuap%2FM%2FvFPiTXsf8hAj%2FALZJ%2FhUT%2BKPEC%2F8AL%2Bf%2B%2FSf%2FABNONuFFRNF%2Fs5PrTUKX8q%2B4fPU%2Fmf3m%2FwCFvEGo3dxPHf3Bl%2B7syirjrnoB7V3KnKg15v4fTZqPIxnFejx%2F6tfpXk4pJVWkrf8ADHo4dt002OooornNhsn%2Brb6Vwgs19K7uT%2FVt9K5pYBXRQny3Oeur2M4Wa%2BlOFl7VprCKkEQrf2xz8plC0x2pwtfatXysU4RA%2BlL2w%2BQyfs3tR9m9q1%2FJHpWdq2qWWkRA3D4ldWMSYJ3kduB7ipliIwXNJ6FQoynLlirsg%2By%2B1Ibb2rkZfHGpKi7ba2UtyAwPTOM9antPF1%2BpSa8jha2JG%2Fy1%2BZV55HNcSzig2lqdzyjEJXsdIbf2FRtbe1ayiOWJZEIKOoZT6g01kHYV6CrHnODRjtbj0FQtAPQVrtF7CoWhHtVqshcj7FTTI%2FL1GP3rvI%2F9Wv0rjLdAmoQYPXP9K7OP%2FVr9K4q0uabZ20laCQ6iiisjQbJ%2Fq2%2BleUxfErS2Uk9V4OGH6eterSf6tvpXxdDqN7N8k8zQJ0%2Fdx5J%2FHtUTbWxrSjCTtI%2BgD8S9IRC7MwAIBPH%2BTWbefEnz9Q09tNkH2MyD7RlQSy5wR7YH0rx%2B1SwiZS4NxIeT5pJ5%2BhrZS%2Bi2hFXauOFwOlctWvJaRud1DCU3rNo9jk%2BIWlwrGZGceY4RRtySTTLrx0j2zfZImDnjdIBgfrXjbvA91FOXczJ0w5wPwq6mpv0Bzg9DWc69Rr3dC4YWhGXvK53U2v6pKuWvrgADPD7f5YrMOr%2F2%2FcIzXv2hIFKsxbdhu4H6Vzd34gEMX2dUWW6mGyOPPOT3PoKi0Cwn0yy8h3DO773K9B049%2BlcNbm9m%2BeWr2PQoKLqJU4qy3fY2rvc2qIAANsiRZA%2Fh6cfmas6g6xWyRRkMT8hP0bP8s1lXQdG81GLMMH6Yz%2FjUomkuLMEuGI5XNcj6M7VBnUWHjSWysdkoeRkjREUeqqAaxZPHWsqDvvTu9BGo%2FpXN36zud0bnIJyoPNY7SzuxBdhjqCP%2Fr17FDEOUFdnjYjCxhUdo7nTH4iao7eSdSlMh9IwP6U2Xxlq8hwL%2BZSMgHNcpI0ueZgSenFN4VcynOf0%2FWt5VL7MxhTtvFHrvw01i%2B1W%2BulvZ5JvKKFS%2BD13Z%2FkK9qj%2FANWv0rwD4NvE%2Bo6l5Zz%2FAKrPP%2B%2FXv8f%2BrX6V0wd43Z59ZJTaQ6iiiqMxsn%2Brb6V8UqxOcL%2F31xX2tJ%2Fq2%2BlfEYl9XGD0PTNZVVsdOH6l5A2OAmfTPNWVafChXTHcFiD%2FACrL8zBH3h9eKeru3Idc%2Bhyf5VzNM7I8pouX5%2F0pVPokZZqdHZT3D7GuGRMcv0YH6DH61SivWjOwKznuoAQD9avLeqwB2dsffHFZT51sdNONKW5radYWlhyihpD1duWb3JrV%2B0g8g%2FSuei1GPcVPTuQev9TUgvEkYs0gCjOMnvXDUpTk7yPRp1KcVaOxrtOXcqPuk43elSiRI0VVxtAwKxlvFRQxkHTCDuT24ppuy0YKOWZjwq9j%2FnrUOizRVl3NOfDkspJ9hWRNb2s5Z2Vlf%2B8rYNJ9qEUgUP8AOF6buuPWop5Uf94GWMnqCa1pwlHYzqShJWkUJ43gJMZZh%2FeA5qnNII8HzCQexHNWpbqEuVeQN3z6VRa6Rd2TGu0dOcn2r0Kal1PKrci2Z6t8EpPM1DUzkceTwO336%2Bho%2FwDVr9K%2Bd%2FghIsupas6EEEwnIXH9%2BvoiP%2FVr9K7IfCeTV%2BNjqKKKszGyf6tvpXw59mt9vFzIW9AcZ%2FOvuOT%2FAFbfSvhAMT9Kiab2NqLir3VzQW0jCYW4bbgnHBNNEUQOPMYn%2B62Mn9KigkiAKyq7AjHDYFSBoYtpjgcEdCZMisdTrSjZNWHbmLExRuyZ6A96ljuySFaElieFVh%2FWmlZpJN63ZCfxbc8fh1psqDaWhlkkfpJu6fmKWjHaS1%2FyL32zagA3MQMbHQAKfw60wXhkYlyMgYBAwVHsBx%2BdUlLZ2yoq%2BhY8H6YqUxxMu0XJjY%2Fw4JU%2Fnip5EivaSZbR4nlGIJnbGd2Rux69OKLnUDEQxbyyAduBg8%2F561SLLGGjXP8AtGOQlWqMBN2RAjufUE0lTV7sbqytZEovpZHMVs7u7Nl3IPP0xzUVw6q5WWRpZF6%2FMAAf60%2BSacAx5A38BUAGfyqEGKE%2FvNpI52qvI%2BprRLyMZNvRslW33zlHXACknA79aRJEDM%2FJdm6Ng1C1xG5yiMrHqd1QN83Cjdn0zVcre4udR%2BE9l%2BB8xkv9UU4%2BXyegx%2Ff%2FAMK%2BiY%2F9Wv0r5w%2BA4Iv9WJ7%2BR%2F7PX0fH%2Fq1%2BlaxVkclRtybY6iiiqIEYZUivErr4A6Ij4hvtUK%2F7Ukf%2FAMRXt1GB6UDTa2PCv%2BFC6Z0N7qZHp5qf%2FEUq%2FAbSl6XWo49DIn%2FxFe6YHpRgelKyDmZ4f%2FwozTv4bvUVHosiD%2F2SkPwL08nJvtS%2F7%2BJ%2F8RXtjTwJPHA8sazSAmOMsAzgYyQO%2BMjP1oM8AuVtjLGJ2QusRYbioIBIHXAJHPuKXKivaT7niJ%2BA%2BmEAG81I49ZE%2FwDiKB8CNMAwLzUv%2B%2Fif%2FEV7gJYjM0IdDKqhmTI3AHIBI9Dg%2Fkahjv7KZo1ju7dzKXWMLIp3lDhgPUgg59KdkLnl3PFj8CdNPW81H%2FvtP%2FiKB8C9PXpfanj081Mf%2BgV7ZdXVrY2z3N3PDbwJjfLM4RVycck8DmpcD2o5UHPLueGH4DaWxybzUvwkQf8AslMPwC0otk3mpZ%2F66J%2F8RXu%2BB6UYHpTsK7Z4SfgHpXa71EfSRP8A4ilHwE0sdLzUf%2B%2B4%2FwD4ivdcD0owPSlYOZnmvgj4b23g%2B5uZLWa5l%2B0bN%2FnspxtzjGFH9416SowoFLgelFMQUUUUAFFFFABRRRQBy3i633XOl3Extp7dLlVS0kQrK83WNoZVIKPkY5%2BUqTkgZNYFzFJpmqQy67qtxb3Euk3U9xcRTEmCRpoSEi45CnCquPm9DuIPYato02o6lYXsd3HEbESvDHJCZFMzLtV2wy5Cgtxxnd1GKbbeHg15Ff6teSaleRHdF5ihIYG7mOIcA9cMxZwCRuwTSS%2FX8Vb%2FAIP3A9fw%2FB3OK8QWuoX1hJNf29je6pLocstvanTy0yupT%2BAqyspLKTG4O1wNu7GRwMfhuNZ4o7TRHmubeVS0T6Y0mBI2TvVtLQEYzjc4AA4IxXtOteFf7Xvrq6d9PmE0EUCw6hYfaYkVGZmO3euSSy89tvesX%2FhU%2Bg2sYksLTTjeEkyPqGmQ3EUufWNQgXAzjyyg9Q1NPW7%2FAK1B7W%2FrY2%2FGtzHpPgjUJ0sbO4jt4l221zFvhIDKACvHA%2FoKqHV%2FEOoeK9X0fTW0y1gsYbeRbi5geZmMisSuxXX0%2B9njpg5yKfizSnOk3ukWx1K6u9YEESQxwubW2CFQzKcFIVwCxBbJxxk0sfh68u%2FH3iK7jvtU0tJILSOO4tlTbMAr7h%2B8RlJBxyBkZ680n3ASy8Z6prEGj2Nlb2dtq149ylxJMGlhg%2BzvskKqCpfccbRuGAeTxg6vhzWNWvNd1zStVFkzacYBHLao6CQOhYkhmOOnTJx6mqmreH9K0nTdIFuur2n9nMy293pkRuJot4%2Bfeu1y4c9SUbnk460zwPplzDqOv6vMt8ItRniELaguyeURptMjJgbAxzhdq4AHAzimtW%2F67f1%2FWifT%2Bv6%2Fr59nRRRQMKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigD%2F9k%3D";
	        
//	        String ss = URLDecoder.decode(strImg1,"utf-8").split(",")[1];
	        String ss ="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";  
	        System.out.println(ss);
	        
	        GenerateImage(ss);
	    }
	    public static String GetImageStr()
	    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        String imgFile = "D:\\QQ截图20150814094814.png";//待处理的图片
	        InputStream in = null;
	        byte[] data = null;
	        //读取图片字节数组
	        try 
	        {
	            in = new FileInputStream(imgFile);        
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        //对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(data);//返回Base64编码过的字节数组字符串
	    }
	    public static boolean GenerateImage(String imgStr)
	    {//对字节数组字符串进行Base64解码并生成图片
	        if (imgStr == null) //图像数据为空
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try 
	        {
	            //Base64解码
	            byte[] b = decoder.decodeBuffer(imgStr);
	            
	            for(int i=0;i<b.length;++i)
	            {
	                if(b[i]<0)
	                {//调整异常数据
	                    b[i]+=256;
	                }
	            }
	            
	            //生成jpeg图片
	            String imgFilePath = "d:\\2223.jpg";//新生成的图片
	            FileUtils.writeByteArrayToFile(new File(imgFilePath), b, true);
	      /*      OutputStream out = new FileOutputStream(imgFilePath);
	            out.write(b);
	            out.flush();
	            out.close();*/
	            return true;
	        } 
	        catch (Exception e) 
	        {
	            return false;
	        }
	    }

}

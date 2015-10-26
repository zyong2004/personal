

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailsTest {

	
	public static void main(String[] args) throws IOException {
		String path = "d:/test/1c571808-e79c-4b32-9f5c-1eb2c598feeb.jpg";
		Thumbnails.of(path).size(140, 140).outputQuality(1).toFile(new File("d:\\test/1111.jpg"));
	}
}

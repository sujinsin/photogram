package com.cos.photogramstart.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class ImageUtil {

	// 2mb
	private static final int maxSize = 2 * 1024 * 1024;
	
	// 가로 세로 중 가장 작은쪽을 1200 으로 제한  
	private static final int targetSize = 1200;

	// 클라이언트로부터 받아온 file sourceFile을 리사이징 
	public static byte[] resize(byte[] sourceFileBytes, String fileNames) throws IOException {
	    // png,jpg 등의 확장자를 추출    
	    String extension = FilenameUtils.getExtension(fileNames);
	    
	    // 이미지 크기가 2mb 초과 4mb 이하일 경우 2mb 로 리사이징
	    if (sourceFileBytes.length > maxSize) {
	        System.out.println("2mb 가 넘는경우 if문 안의 ..  ");
	        
	        BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(sourceFileBytes));
	        
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        ImageIO.write(resizeImage(sourceImage, targetSize), extension, os);
	        
	        return os.toByteArray();
	    }
	    
	    System.out.println("2mb 가 안넘는 경우");

	    // 이미지 크기가 2MB 이하인 경우
	    BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(sourceFileBytes));
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    ImageIO.write(sourceImage, extension, os);

	    return os.toByteArray();
	}



	
	
	
	private static BufferedImage resizeImage(BufferedImage sourceImage, int targetSize) {
		BufferedImage targetImage = new BufferedImage(targetSize, targetSize, Transparency.TRANSLUCENT);
		Graphics2D g2 = targetImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.drawImage(sourceImage, 0, 0, targetSize, targetSize, null);
		g2.dispose();
		return targetImage;
	}

}
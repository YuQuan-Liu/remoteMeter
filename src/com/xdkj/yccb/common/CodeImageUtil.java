package com.xdkj.yccb.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 验证码
 * @author SW
 *
 */
@Controller
public class CodeImageUtil{
	@RequestMapping(value="/resource/codeImg",method=RequestMethod.GET)
	public void getImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("pragma", "No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		
		int width = 60,height=20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		Random r = new Random();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
		
		String check ="";
		String rand = "0";
		for(int i = 0;i <4;i++){
			rand = r.nextInt(10)+"";
			check += rand;
			if(i == 0 || i ==2){
				g.setFont(new Font("Times New Roman", Font.ITALIC, 18));
			}else{
				g.setFont(new Font("Times New Roman", Font.BOLD, 14));
			}
			g.setColor(Color.BLACK);
			g.drawString(rand, 13*i+6, 16);
		}
		//这个地方还没有登录进系统  不能用false 如果用false 则会一直是null
		HttpSession session = request.getSession(true);
		session.setAttribute("check", check);
		
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

}

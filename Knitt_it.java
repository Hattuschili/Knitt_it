import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import ij.plugin.PlugIn.*;
import ij.process.ImageConverter;
import java.awt.Label;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.awt.image.BufferedImage;

public class Knitt_it implements PlugIn {

	public void run(String arg) {
		ImagePlus imp = IJ.getImage();
		int w = imp.getWidth();
		int h = imp.getHeight();
		String[] test = {"■","□","▲","△","○","●","▶","▷","◀","◁","L","M","O","P","R","S","T","X","Y","Z"};
		double stitches = 100;
		double farben = 1;
		double wool = 1;
		//Dialog for factor entry
		GenericDialog gd = new GenericDialog("Adaptation");
		gd.addNumericField("Stitches:",stitches,0);
		gd.addNumericField("No. of Colors:",farben,0);
		gd.addNumericField("Wool Factor:",wool,2);
		gd.showDialog();
		stitches = gd.getNextNumber();
		farben = gd.getNextNumber();
		wool = gd.getNextNumber();
		double was = 256/farben;
		//Convert image to grayscale
		ImageConverter ic = new ImageConverter(imp);
		ic.convertToGray8();
		//Scale image to entered size and relation values
		ImageProcessor imp1a = imp.getProcessor();
		imp1a.setInterpolationMethod(ImageProcessor.BILINEAR);
		double hoehe = ((stitches/w)*h)*wool;
		int hochint = (int)hoehe;
		int stitchint = (int)stitches;		
		imp1a = imp1a.resize(stitchint,hochint);
		new ImagePlus("resized Image", imp1a).show();
		//Generate symbol logfile according to number of colors
		String B = "";
		String A = "";
		int farbindex = 0;
		for (int y=0; y<hochint;y++){
			for (int x= 0; x < stitchint;x++) {
				int wert = imp1a.get(x,y);
				while (wert >= was*farbindex) {
					A = test[farbindex];
					farbindex++;}
				B = B + A + " ";
				farbindex = 0;	}
		 
			IJ.log(B);
			B = "";}
		for (int i=0; i < farben; i ++){
			String w0 = ""+test[i]+"="+was*i+"-"+was*(i+1);
			IJ.log(w0);}
		

	}

}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {
        private int i=1,sum=0,threshold=0;
        private OpenFileDialog ofd=null;
        private Image img = null;
        private Boolean flag = false,flag1=false;
        public Form1()
        {
            InitializeComponent();
        }
        //Browse Button CODE
        private void button1_Click(object sender, EventArgs e)
        {
            ofd = new OpenFileDialog();
            ofd.Filter = "Image files (*.jpg, *.jpeg, *.jpe, *.jfif, *.png) | *.jpg; *.jpeg; *.jpe; *.jfif; *.png";
            //ofd.InitialDirectory = @"C:\Users\Hi\Pictures\Screenshots";
            if (ofd.ShowDialog() == DialogResult.OK)
            {
                string filename = ofd.FileName;
                textBox1.Text = filename;
                try { img = Image.FromFile(filename); }
                catch(Exception exp) {  }
                pictureBox1.Image = ReSize(img,400,400);
            }
        }
        private static Image ReSize(Image i,int w,int h)
        {
            Bitmap bmp = new Bitmap(w, h);
            Graphics graphics = Graphics.FromImage(bmp);
            graphics.DrawImage(i, 0, 0, w, h);
            graphics.Dispose();
            return bmp;
        }
        //GrayScale Image Button CODE
        private void button2_Click(object sender, EventArgs e)
        {
            if (ofd!= null && textBox1.Text!="" && textBox1.Text == ofd.FileName)
            {
                img = ReSize(img,400,400);
                int width = img.Width;
                int height = img.Height;
                Image temp = img;
                pictureBox2.Image =grayscale(temp, width, height);
            }
            else
            {
                MessageBox.Show("Please Provide Image Location To Convert.");
            }
        }
        private Image grayscale(Image ip,int width,int height)
        {
            Bitmap bm = new Bitmap(ip);
            Color p;
            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    p = bm.GetPixel(i, j);
                    int a = p.A;
                    int r = p.R;
                    int b = p.B;
                    int g = p.G;
                    int avg = (r + g + b) / 3;
                    sum += avg;
                    bm.SetPixel(i, j, Color.FromArgb(a, avg, avg, avg));
                }
            }
            threshold = sum / (width * height);
            flag1 = true;
            return bm;
        }
        //save image button CODE
        private void button3_Click(object sender, EventArgs e)
        {
            Image temp = pictureBox2.Image;
            if (temp != null && flag == true)
            {
                saveFileDialog1.InitialDirectory = textBox3.Text;
                saveFileDialog1.FileName = "Untitled"+(i)+".jpg";
                saveFileDialog1.Filter = "Image files (*.jpg, *.jpeg, *.jpe, *.jfif, *.png) | *.jpg; *.jpeg; *.jpe; *.jfif; *.png"; ;
                if (saveFileDialog1.ShowDialog() == DialogResult.OK)
                {
                    i++;
                    Bitmap bm = new Bitmap(img);
                    bm.Save(saveFileDialog1.FileName);
                    MessageBox.Show("File has been saved.");
                }
            }
            else if (flag == false && img != null)
            {
                MessageBox.Show("Specify the location to save.");
            }
            else
            {
                MessageBox.Show("Convert an Image to GrayScale to Save.");
            }
        }
       

        //Binary Image Button CODE
        private void button4_Click(object sender, EventArgs e)
        {
            //int w=0, h=0;
            if (pictureBox1.Image != null)
            {
                Form2 fn = new Form2();
                Image img1 =ReSize(pictureBox1.Image,400,200);
                Image temp = img1;
                if (flag1 == false)
                {
                    temp = grayscale(temp, temp.Width, temp.Height);
                }
                flag1=fn.setValue(img1,this,threshold);
                fn.Show();
                this.Hide();
            }
            else
            {
                MessageBox.Show("Please provide Image location to convert");
            }
        }
        //folder Browser Button CODE
        private void button5_Click(object sender, EventArgs e)
        {
            if(folderBrowserDialog1.ShowDialog()==DialogResult.OK)
            {
                textBox3.Text = folderBrowserDialog1.SelectedPath;
                flag = true;
            }
        }
        //Black and white image CODE
        private void button6_Click(object sender, EventArgs e)
        {
            if (ofd != null && textBox1.Text != "" && textBox1.Text == ofd.FileName)
            {
                img = ReSize(img, 400, 400);
                Image temp = img;
                if (flag1 == false)
                {
                    temp = grayscale(temp, temp.Width, temp.Height);
                }
                Bitmap bm = new Bitmap(img);
                int width = bm.Width;
                int height = bm.Height;
                Color p;
                for (int i = 0; i < width; i++)
                {
                    for (int j = 0; j < height; j++)
                    {
                        p = bm.GetPixel(i, j);
                        int a = p.A;
                        int r = p.R;
                        int b = p.B;
                        int g = p.G;
                        int avg = (r + g + b) / 3;
                        if (avg<=threshold)
                            bm.SetPixel(i, j, Color.FromArgb(a, 255, 255, 255));
                        else
                            bm.SetPixel(i, j, Color.FromArgb(a, 0, 0, 0));
                    }
                }
                sum = 0;
                threshold = 0;
                flag1 = false;
                pictureBox2.Image = bm;
            }
            else
            {
                MessageBox.Show("Please Provide Image Location To Convert.");
            }
        }
    }
}

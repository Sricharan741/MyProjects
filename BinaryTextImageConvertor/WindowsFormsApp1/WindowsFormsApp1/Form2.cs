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
    public partial class Form2 : Form
    {
        private Form1 ob = null;
        public Form2()
        {
            InitializeComponent();
        }
        public Boolean setValue(Image img1,Form1 obj,int t)
        {
            ob = obj;
            Bitmap img = new Bitmap(img1);
            string texto = "";
            for (int i = 0; i < img.Height; i++)
            {
                for (int j = 0; j < img.Width; j++)
                {
                    if (((img.GetPixel(j, i).B+img.GetPixel(j, i).G+img.GetPixel(j, i).R)/3) <= t)
                    {
                        texto = texto + "0";
                        /* textBox2.ForeColor = Color.FromArgb(img.GetPixel(j, i).A, img.GetPixel(j, i).R, img.GetPixel(j, i).G, img.GetPixel(j, i).B);
                         if (j % 2 == 0)
                             textBox2.Text += "0";    //texto = texto + "0";
                         else
                             textBox2.Text += "1";*/
                    }
                    else
                    {
                        texto = texto + "1";
                    }
                    //w++;
                }
                //textBox2.Text += "\r\n";
                texto = texto + "\r\n";
                // h++;
            }
            textBox1.Text = texto;
            return true;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Hide();
            ob.Show();
        }
        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            this.Hide();
            ob.Show();
        }
    }
}

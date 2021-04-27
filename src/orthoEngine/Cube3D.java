/*     */ package orthoEngine;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cube3D
/*     */ {
/*  17 */   public Node[] nodes = new Node[8];
/*  18 */   public ZGetter[] stuff = new ZGetter[18];
/*  19 */   public int[] order = new int[this.stuff.length]; public double[] size; public double[] translation; public Cube3D(double[] size, double[] translation, double[] COR, Color cl) {
/*  20 */     int c = 0;
/*  21 */     this.size = size;
/*  22 */     this.translation = translation;
/*  23 */     this.COR = COR;
/*     */     
/*  25 */     for (int i = 0; i < 2; i++) {
/*  26 */       for (int j = 0; j < 2; j++) {
/*  27 */         for (int k = 0; k < 2; k++) {
/*  28 */           this.nodes[c] = new Node(i * size[0] - size[0] / 2.0D + translation[0] - COR[0], j * size[1] - size[1] / 2.0D + translation[1] - COR[1], k * size[2] - size[2] / 2.0D + translation[2] - COR[2], 2);
/*  29 */           c++;
/*     */         } 
/*     */       } 
/*     */     } 
/*  33 */     this.stuff[0] = new Edge(this.nodes[0], this.nodes[1], 2);
/*  34 */     this.stuff[1] = new Edge(this.nodes[1], this.nodes[3], 2);
/*  35 */     this.stuff[2] = new Edge(this.nodes[3], this.nodes[2], 2);
/*  36 */     this.stuff[3] = new Edge(this.nodes[2], this.nodes[0], 2);
/*  37 */     this.stuff[4] = new Edge(this.nodes[4], this.nodes[5], 2);
/*  38 */     this.stuff[5] = new Edge(this.nodes[5], this.nodes[7], 2);
/*  39 */     this.stuff[6] = new Edge(this.nodes[7], this.nodes[6], 2);
/*  40 */     this.stuff[7] = new Edge(this.nodes[6], this.nodes[4], 2);
/*  41 */     this.stuff[8] = new Edge(this.nodes[0], this.nodes[4], 2);
/*  42 */     this.stuff[9] = new Edge(this.nodes[1], this.nodes[5], 2);
/*  43 */     this.stuff[10] = new Edge(this.nodes[2], this.nodes[6], 2);
/*  44 */     this.stuff[11] = new Edge(this.nodes[3], this.nodes[7], 2);
/*     */     
/*  46 */     this.stuff[12] = new Face(new Node[] { this.nodes[0], this.nodes[1], this.nodes[3], this.nodes[2] }, cl);
/*  47 */     this.stuff[13] = new Face(new Node[] { this.nodes[1], this.nodes[3], this.nodes[7], this.nodes[5] }, cl);
/*  48 */     this.stuff[14] = new Face(new Node[] { this.nodes[0], this.nodes[1], this.nodes[5], this.nodes[4] }, cl);
/*  49 */     this.stuff[15] = new Face(new Node[] { this.nodes[4], this.nodes[5], this.nodes[7], this.nodes[6] }, cl);
/*  50 */     this.stuff[16] = new Face(new Node[] { this.nodes[0], this.nodes[2], this.nodes[6], this.nodes[4] }, cl);
/*  51 */     this.stuff[17] = new Face(new Node[] { this.nodes[2], this.nodes[3], this.nodes[7], this.nodes[6] }, cl);
/*     */     
/*  53 */     sortOrder();
/*     */   }
/*     */   public double[] COR; public Color cl;
/*     */   public void setColor(Color c) {
/*  57 */     for (int i = 0; i < 6; i++) {
/*  58 */       this.stuff[i + 12].setColor(c);
/*     */     }
/*     */   }
/*     */   
/*     */   public void rotateZ(double theta) {
/*  63 */     for (int i = 0; i < this.nodes.length; i++) {
/*  64 */       Node node = this.nodes[i];
/*  65 */       double x = node.x;
/*  66 */       double y = node.y;
/*  67 */       node.x = x * Math.cos(theta) - y * Math.sin(theta);
/*  68 */       node.y = y * Math.cos(theta) + x * Math.sin(theta);
/*     */     } 
/*     */   }
/*     */   public void rotateY(double theta) {
/*  72 */     for (int i = 0; i < this.nodes.length; i++) {
/*  73 */       Node node = this.nodes[i];
/*  74 */       double x = node.x;
/*  75 */       double z = node.z;
/*  76 */       node.x = x * Math.cos(theta) - z * Math.sin(theta);
/*  77 */       node.z = z * Math.cos(theta) + x * Math.sin(theta);
/*     */     } 
/*     */   }
/*     */   public void rotateX(double theta) {
/*  81 */     for (int i = 0; i < this.nodes.length; i++) {
/*  82 */       Node node = this.nodes[i];
/*  83 */       double y = node.y;
/*  84 */       double z = node.z;
/*  85 */       node.y = y * Math.cos(theta) - z * Math.sin(theta);
/*  86 */       node.z = z * Math.cos(theta) + y * Math.sin(theta);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sortOrder() {
/*  92 */     double[] k = new double[this.stuff.length];
/*     */     
/*  94 */     for (int i = 0; i < this.stuff.length; i++) {
/*  95 */       this.order[i] = i;
/*  96 */       k[i] = this.stuff[i].averageZ();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     for (int j = 0; j < this.stuff.length - 1; j++) {
/* 102 */       for (int m = 0; m < this.stuff.length - 1; m++) {
/* 103 */         if (k[m] < k[m + 1]) {
/* 104 */           double t2 = k[m];
/* 105 */           int t = this.order[m];
/*     */           
/* 107 */           this.order[m] = this.order[m + 1];
/* 108 */           k[m] = k[m + 1];
/*     */           
/* 110 */           this.order[m + 1] = t;
/* 111 */           k[m + 1] = t2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void draw(Graphics g) {
/* 118 */     Graphics2D g2 = (Graphics2D)g;
/* 119 */     g2.translate((int)this.COR[0], (int)this.COR[1]);
/* 120 */     for (int i = 0; i < this.stuff.length; i++) {
/* 121 */       this.stuff[this.order[i]].draw(g2);
/*     */     }
/* 123 */     g2.translate((int)-this.COR[0], (int)-this.COR[1]);
/*     */   }
/*     */ }


/* Location:              C:\Users\hazya\OneDrive\Desktop\progr_proj\Tetris-3D-master\Build\Tetris3D Public.jar!\orthoEngine\Cube3D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
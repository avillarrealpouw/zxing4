/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.google.zxing.client.j2se;

/**
 *
 * @author avillarr
 */
public class BitArray3 {
  public byte[] a;
  public int maxsize;
  public boolean fillbit;
  public double endpoint;

  public BitArray3(boolean fillb,int size){
    int i1=size/32;
    int i2=i1*32;
    if(size>i2){
        i2++;
        i1=i2/32;
    }
    this.maxsize=i1*4;
    this.a = new byte[maxsize];
    if (fillb){
        this.fillbit=true;
        for(int i=0;i<this.maxsize;i++){
            this.a[i]=(byte)0xff;
        }
    }else{
        this.fillbit=false;
        for(int i=0;i<this.maxsize;i++){
            this.a[i]=(byte)0x00;
        }
    }
    this.endpoint=0.0;
    this.fillbit=fillb;
  }
  public void append(boolean b, double l){
      int ix=(int)this.endpoint;
      while(ix<this.endpoint+l){
          int iy=ix/8;
          int iz=ix%8;
          int m=1<<(7-iz);
          int m2=this.a[iy];
          if(b){
              int n=m2|m;
              this.a[iy]=(byte)n;
          }else{
              int n=m2&(~m);
              this.a[iy]=(byte)n;
          }
          ix++;
      }
      this.endpoint+=l;
  }
}
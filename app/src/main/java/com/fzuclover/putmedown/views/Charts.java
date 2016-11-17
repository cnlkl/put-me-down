package com.fzuclover.putmedown.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/11/17.
 */

public class Charts extends View {

    private int mCavansWidth=300;
    private int mCavansHeight=300;
    private Paint mLinePaint;
    private int xLength;
    private int yLength;
    private int xScale;
    private int yScale;
    private int xCount=7;
    private int yCount=6;
    private int oX;
    private int oY;
    private int TextSize;
    private int xSpluse=150;
    private int ySpluse=150;
    private  int PointColor=Color.GREEN;
    private  int LineColor=Color.BLUE;
    private String[] xText={"一","二","三","四","五","六","日"};
    private String[] yText={"0","100","200","300","400","500","600"};



    private int[] targetData={500,500,500,500,500,500,500};



    private int[] data={100,200,100,500,400,100,50};
    public Charts(Context context) {
        super(context);

    }
    public Charts(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public Charts(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public void setData(int[] data) {
        this.data = data;
    }
    public int[] getData() {
        return data;
    }
    public int[] getTargetData() {
        return targetData;
    }

    public void setTargetData(int[] targetData) {
        this.targetData = targetData;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCavansWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mCavansHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(mCavansWidth, mCavansHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        inits();
        mLinePaint=new Paint();
        mLinePaint.setColor(this.LineColor);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(5);
        //绘制坐标轴
        canvas.drawLine(oX,oY,oX+xLength+80,oY,mLinePaint);
        canvas.drawLine(oX,oY-yLength-80,oX,oY,mLinePaint);
        //绘制刻度
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(48);
        for(int i=0;i<this.xCount;i++){
            canvas.drawLine(oX+(i+1)*xScale,oY,oX+(i+1)*xScale,oY-15,mLinePaint);
            canvas.drawText(xText[i],oX+(i+1)*xScale,oY+50,textPaint);
        }
        canvas.drawLine(oX+(6+1)*xScale+80,oY,oX+(6+1)*xScale+40,oY-35,mLinePaint);
        canvas.drawLine(oX+(6+1)*xScale+80,oY,oX+(6+1)*xScale+40,oY+35,mLinePaint);
        for(int i=0;i<=this.yCount;i++){
            canvas.drawLine(oX,oY-i*yScale,oX+15,oY-i*yScale,mLinePaint);
            canvas.drawText(yText[i],oX-90,oY-i*yScale,textPaint);
        }
        canvas.drawText("min",oX-90,oY-6*yScale-80,textPaint);
        canvas.drawLine(oX,oY-6*yScale-80,oX+35,oY-6*yScale-40,mLinePaint);
        canvas.drawLine(oX,oY-6*yScale-80,oX-35,oY-6*yScale-40,mLinePaint);
        drawline(canvas);
    }

   public void drawline(Canvas canvas)
   {
       Path path=new Path();
       Paint pointPaint=new Paint();
       Paint linePaint=new Paint();
       Paint targetPaint=new Paint();
       Path target=new Path();
       mLinePaint.setTextSize(48);
       //折点画笔
       pointPaint.setColor(Color.GREEN);
       pointPaint.setStyle(Paint.Style.FILL);
       pointPaint.setStrokeWidth(20);
       pointPaint.setAntiAlias(true);
       //折线画笔
       linePaint.setColor(Color.BLUE);
       linePaint.setStyle(Paint.Style.STROKE);
       linePaint.setAntiAlias(true);
       //折线图标
       canvas.drawPoint(520+100,120,pointPaint);
       canvas.drawText("-- 达成时间",600+100,130,mLinePaint);
       path.moveTo(oX+xScale,(int)(oY-data[0]*0.01*yScale));
       canvas.drawPoint(oX+xScale,(int)(oY-data[0]*0.01*yScale),pointPaint);
       canvas.drawText(data[0]+"",oX+(0+1)*xScale,(int)(oY-data[0]*0.01*yScale),mLinePaint);

       for(int i=1;i<xCount;i++){
           canvas.drawPoint(oX+(i+1)*xScale,(int)(oY-data[i]*0.01*yScale),pointPaint);
           canvas.drawText(data[i]+"",oX+(i+1)*xScale,(int)(oY-data[i]*0.01*yScale),mLinePaint);
           path.lineTo(oX+(i+1)*xScale,(int)(oY-data[i]*0.01*yScale));
           canvas.drawPath(path,linePaint);
       }
       //目标折点画笔
       targetPaint.setColor(Color.RED);
       targetPaint.setStyle(Paint.Style.FILL);
       targetPaint.setStrokeWidth(20);
       targetPaint.setAntiAlias(true);
       //目标折线画笔
       linePaint.setColor(Color.BLUE);
       linePaint.setStyle(Paint.Style.STROKE);
       linePaint.setAntiAlias(true);
       // 折线图标
       canvas.drawPoint(520+100,40,targetPaint);
       canvas.drawText("-- 目标时间",600+100,50,mLinePaint);

       target.moveTo(oX+xScale,(int)(oY-targetData[0]*0.01*yScale));
       canvas.drawPoint(oX+xScale,(int)(oY-targetData[0]*0.01*yScale),targetPaint);
       canvas.drawText(targetData[0]+"",oX+(0+1)*xScale,(int)(oY-targetData[0]*0.01*yScale),mLinePaint);
       mLinePaint.setTextSize(48);
       for(int i=1;i<xCount;i++){
           canvas.drawPoint(oX+(i+1)*xScale,(int)(oY-targetData[i]*0.01*yScale),targetPaint);
           canvas.drawText(targetData[i]+"",oX+(i+1)*xScale,(int)(oY-targetData[i]*0.01*yScale),mLinePaint);
           target.lineTo(oX+(i+1)*xScale,(int)(oY-targetData[i]*0.01*yScale));
           canvas.drawPath(target,linePaint);
       }

   }
    public void inits()
    {
        //坐标轴长度
        this.xLength=this.mCavansWidth-2*xSpluse;
        this.yLength=this.mCavansHeight-2*ySpluse;
        //原点位置
        this.oX=this.xSpluse;
        this.oY=this.mCavansHeight-ySpluse;
        //刻度间距
        this.xScale=this.xLength/this.xCount;
        this.yScale=this.yLength/this.yCount;

    }
}

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
    private int xLength;
    private int yLength;
    private int xScale;
    private int yScale;
    private int xCount=10;
    private int yCount=6;
    private int oX;
    private int oY;
    private int TextSize;
    private int xSpluse=150;
    private int ySpluse=150;
    private  int PointColor=Color.GREEN;
    private  int LineColor=Color.BLUE;
    private String[] xText={"1","2","3","4","5","6","7","8","9","10"};
    private String[] yText={"0","100","200","300","400","500","600"};
    private int[] targetData;
    private int[] data;
    private Paint coordinatePaint;
    private Paint textPaint;
    private Paint textPaint2;
    private Paint mLinePaint;
    private String ChartName="距今I天当天目标与实际达成时间对比图";
    private String xName="/I";
    private String yName="/min";
    private String line_1Name="达成时间";
    private String line_2Name="目标时间";


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
        //绘制坐标轴
        canvas.drawLine(oX,oY,oX+xLength+80,oY,coordinatePaint);
        canvas.drawLine(oX,oY-yLength-80,oX,oY,coordinatePaint);
        //绘制刻度
        for(int i=0;i<this.xCount;i++){
            canvas.drawLine(oX+(i+1)*xScale,oY,oX+(i+1)*xScale,oY-15,coordinatePaint);
            canvas.drawText(xText[i],oX+(i+1)*xScale,oY+50,textPaint);
        }

        canvas.drawText(xName,oX+xCount*xScale+80,oY+50,textPaint);
        canvas.drawLine(oX+(9+1)*xScale+80,oY,oX+(9+1)*xScale+40,oY-35,coordinatePaint);
        canvas.drawLine(oX+(9+1)*xScale+80,oY,oX+(9+1)*xScale+40,oY+35,coordinatePaint);
        for(int i=0;i<=this.yCount;i++){
            canvas.drawLine(oX,oY-i*yScale,oX+xLength+60,oY-i*yScale,coordinatePaint);
            canvas.drawText(yText[i],oX-90,oY-i*yScale,textPaint);
        }
        canvas.drawText(yName,oX-90,oY-6*yScale-80,textPaint);
        canvas.drawLine(oX,oY-6*yScale-80,oX+35,oY-6*yScale-40,coordinatePaint);
        canvas.drawLine(oX,oY-6*yScale-80,oX-35,oY-6*yScale-40,coordinatePaint);
        canvas.drawText(ChartName,oX+0*xScale,oY+120,textPaint);
        drawline(canvas);
    }

   public void drawline(Canvas canvas)
   {
       Path path=new Path();
       Paint pointPaint=new Paint();
       Paint targetPaint=new Paint();
       Path target=new Path();
       //折点画笔
       pointPaint.setColor(Color.rgb(204,204,0));
       pointPaint.setStyle(Paint.Style.FILL);
       pointPaint.setStrokeWidth(20);
       pointPaint.setAntiAlias(true);
       //折线图标
       canvas.drawPoint(520+100,100,pointPaint);
       textPaint.setTextSize(40);
       canvas.drawText("--"+line_1Name,600+100,110,textPaint);
       textPaint.setTextSize(48);
       path.moveTo(oX+xScale,(int)(oY-data[0]*0.01*yScale));
       canvas.drawPoint(oX+xScale,(int)(oY-data[0]*0.01*yScale),pointPaint);
       canvas.drawText(data[0]+"",oX+(0+1)*xScale,(int)(oY-data[0]*0.01*yScale-10),textPaint2);

       for(int i=1;i<xCount;i++){
           canvas.drawPoint(oX+(i+1)*xScale,(int)(oY-data[i]*0.01*yScale),pointPaint);
           canvas.drawText(data[i]+"",oX+(i+1)*xScale,(int)(oY-data[i]*0.01*yScale-10),textPaint2);
           path.lineTo(oX+(i+1)*xScale,(int)(oY-data[i]*0.01*yScale));
           canvas.drawPath(path,mLinePaint);
       }
       //目标折点画笔
       targetPaint.setColor(Color.rgb(255,153,102));
       targetPaint.setStyle(Paint.Style.FILL);
       targetPaint.setStrokeWidth(20);
       targetPaint.setAntiAlias(true);
       // 折线图标
       canvas.drawPoint(520+100,20,targetPaint);
       textPaint.setTextSize(40);
       canvas.drawText("--"+line_2Name,600+100,30,textPaint);
       textPaint.setTextSize(48);
       target.moveTo(oX+xScale,(int)(oY-targetData[0]*0.01*yScale));
       canvas.drawPoint(oX+xScale,(int)(oY-targetData[0]*0.01*yScale),targetPaint);
       canvas.drawText(targetData[0]+"",oX+(0+1)*xScale,(int)(oY-targetData[0]*0.01*yScale-10),textPaint2);
       mLinePaint.setTextSize(48);
       for(int i=1;i<xCount;i++){
           canvas.drawPoint(oX+(i+1)*xScale,(int)(oY-targetData[i]*0.01*yScale),targetPaint);
           canvas.drawText(targetData[i]+"",oX+(i+1)*xScale,(int)(oY-targetData[i]*0.01*yScale-10),textPaint2);
           target.lineTo(oX+(i+1)*xScale,(int)(oY-targetData[i]*0.01*yScale));
           canvas.drawPath(target,mLinePaint);
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
        coordinatePaint=new Paint();
        coordinatePaint.setColor(Color.rgb(204,204,204));
        coordinatePaint.setAntiAlias(true);
        coordinatePaint.setStyle(Paint.Style.STROKE);
        coordinatePaint.setStrokeWidth(5);
        mLinePaint=new Paint();
        mLinePaint.setColor(Color.rgb(102,51,153));
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(48);
        textPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint2.setColor(Color.BLACK);
        textPaint2.setTextSize(30);

    }
}

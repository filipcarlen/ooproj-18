package se.chalmers.grupp18.v01;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.GL20;

public class Draw extends DebugDraw{

	public Draw(IViewportTransform viewport) {
		super(viewport);

		GL20 gl;
	}

	@Override
	public void drawCircle(Vec2 arg0, float arg1, Color3f arg2) {
		
	}

	@Override
	public void drawPoint(Vec2 arg0, float arg1, Color3f arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSegment(Vec2 arg0, Vec2 arg1, Color3f arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSolidCircle(Vec2 arg0, float arg1, Vec2 arg2, Color3f arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSolidPolygon(Vec2[] arg0, int arg1, Color3f arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawString(float arg0, float arg1, String arg2, Color3f arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawTransform(Transform arg0) {
		// TODO Auto-generated method stub
		
	}

}

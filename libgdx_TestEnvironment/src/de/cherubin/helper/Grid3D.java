package de.cherubin.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class Grid3D {

	public int halflineCount = 15;
	public float color = Color.toFloatBits(140, 140, 140, 100);
	public float centerColor = Color.toFloatBits(40, 100, 200, 100);
	public float space = 0.3f;
	private Mesh gridMesh;
	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	private boolean useX = true;
	private boolean useY = true;
	private boolean useZ = true;

	private int getLineCount() {
		return (halflineCount * 2 + 1) * 2 * 4;
	}

	private float getRange() {
		return halflineCount * space;
	}

	public void create() {
		if (gridMesh == null) {
			generateLineLayer();
		}
	}

	private float getSpace() {
		return space;
	}

	public void updateGrid() {
		generateLineLayer();
	}

	public void enableLayer(int layer) {
		switch (layer) {
		case X:
			useX = true;
			break;
		case Y:
			useY = true;
			break;
		case Z:
			useZ = true;
			break;
		}
	}

	public void disableLayer(int layer) {
		switch (layer) {
		case X:
			useX = false;
			break;
		case Y:
			useY = false;
			break;
		case Z:
			useZ = false;
			break;
		}
	}

	private void generateLineLayer() {

		gridMesh = new Mesh(true, getLineCount(), getLineCount(), new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
		int e;
		float[] vertices = new float[getLineCount()];
		short[] indices = new short[getLineCount()];
		int count = 0;
		for (int d = -halflineCount; d <= halflineCount; d++) {
			e = (d + halflineCount) * 8;
			vertices[e] = (float) d * getSpace();
			vertices[e + 1] = -getRange();
			vertices[e + 2] = 0;
			if(d==0){
				vertices[e + 3] = centerColor;
				vertices[e + 7] = centerColor;
			}
			else{
				vertices[e + 3] = color;
				vertices[e + 7] = color;
			}
			vertices[e + 4] = d * getSpace();
			vertices[e + 5] = getRange();
			vertices[e + 6] = 0;
			
			for (int i = 0; i < 8; i++) {
				indices[e + i] = (short) (e + i);
			}
		}

		gridMesh.setVertices(vertices);
		gridMesh.setIndices(indices);
		printInfo(gridMesh);
	}

	public void printInfo(Mesh gridMesh) {
		System.out.println("<< Grid3D- INFO >>");
		System.out.println("Anzahl Vertices: " + gridMesh.getNumVertices());
		System.out.println("Anzahl Indices: " + gridMesh.getNumIndices());
		System.out.println("Anzahl Linien: " + getLineCount());
	}

	public void render(int glLines) {
		// TODO Auto-generated method stub
		if (useZ) {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glRotatef(90, 0, 0, 1);
			gridMesh.render(GL10.GL_LINES, 0, gridMesh.getNumVertices());
			Gdx.gl11.glRotatef(90, 0, 0, 1);
			gridMesh.render(GL10.GL_LINES, 0, gridMesh.getNumVertices());
			Gdx.gl11.glPopMatrix();
		}
		if (useY) {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glRotatef(90, 1, 0, 0);
			gridMesh.render(GL10.GL_LINES, 0, gridMesh.getNumVertices());
			Gdx.gl11.glRotatef(90, 0, 0, 1);
			gridMesh.render(GL10.GL_LINES, 0, gridMesh.getNumVertices());
			Gdx.gl11.glPopMatrix();
		}
		if (useX) {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glRotatef(90, 0, 1, 0);
			gridMesh.render(GL10.GL_LINES, 0, gridMesh.getNumVertices());
			Gdx.gl11.glRotatef(90, 0, 0, 1);
			gridMesh.render(GL10.GL_LINES, 0, gridMesh.getNumVertices());
			Gdx.gl11.glPopMatrix();
		}
	}
}

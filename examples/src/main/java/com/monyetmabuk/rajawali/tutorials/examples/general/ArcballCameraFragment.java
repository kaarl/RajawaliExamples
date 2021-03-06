package com.monyetmabuk.rajawali.tutorials.examples.general;

import android.app.Activity;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.cameras.ArcballCamera;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderAWD;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.util.debugvisualizer.DebugVisualizer;
import org.rajawali3d.util.debugvisualizer.GridFloor;

/**
 * Drag to rotate and pinch to zoom.
 */
public class ArcballCameraFragment extends AExampleFragment {
    @Override
    public AExampleRenderer createRenderer() {
        return new ArcballCameraRenderer(getActivity());
    }

    private final class ArcballCameraRenderer extends AExampleRenderer {
        public ArcballCameraRenderer(Context context) {
            super(context);
        }

        @Override
        protected void initScene() {
            try {
                DirectionalLight light = new DirectionalLight();
                light.setLookAt(1, -1, 1);
                light.enableLookAt();
                light.setPower(1.5f);
                getCurrentScene().addLight(light);

                light = new DirectionalLight();
                light.setLookAt(-1, 1, -1);
                light.enableLookAt();
                light.setPower(1.5f);
                getCurrentScene().addLight(light);

                DebugVisualizer debugViz = new DebugVisualizer(this);
                debugViz.addChild(new GridFloor());
                getCurrentScene().addChild(debugViz);

                final LoaderAWD parser = new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.awd_suzanne);
                parser.parse();

                final Object3D monkey = parser.getParsedObject();

                Material material = new Material();
                material.enableLighting(true);
                material.setDiffuseMethod(new DiffuseMethod.Lambert());
                material.setColor(0x990000);

                monkey.setMaterial(material);
                getCurrentScene().addChild(monkey);

                material = new Material();
                material.enableLighting(true);
                material.setDiffuseMethod(new DiffuseMethod.Lambert());
                material.setColor(0x999900);

                Object3D monkey2 = monkey.clone();
                monkey2.setMaterial(material);
                monkey2.setPosition(-3, 3, 3);
                getCurrentScene().addChild(monkey2);

                ArcballCamera arcball = new ArcballCamera(mContext, ((Activity)mContext).findViewById(R.id.drawer_layout));
                arcball.setPosition(4, 4, 4);
                getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}

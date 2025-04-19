# 8Bit Client
## 1.21.1 client 

free cam must be added code 
 @RegisterModule(name = "FreecamSmooth", description = "Smooth camera rotation to block you're looking at", category = Module.Category.MISC)
public class ModuleFreecamSmooth extends Module {

    private float camYaw, camPitch;

    private final ValueBoolean rotateToBlock = new ValueBoolean("RotateToBlock", "Rotate to block", true);
    private final ValueNumber smoothness = new ValueNumber("Smoothness", "How smooth rotation is", 0.2, 0.01, 1.0);

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        camYaw = mc.player.getYaw();
        camPitch = mc.player.getPitch();
    }

    @Override
    public void onTick() {
        if (mc.player == null || !rotateToBlock.getValue()) return;

        HitResult hit = mc.crosshairTarget;
        if (hit instanceof BlockHitResult bhr) {
            Vec3d target = Vec3d.ofCenter(bhr.getBlockPos());
            Vec3d from = mc.player.getEyePos();
            float[] rot = getRotations(from, target);

            camYaw = smooth(camYaw, rot[0], smoothness.getValue().floatValue());
            camPitch = smooth(camPitch, rot[1], smoothness.getValue().floatValue());

            mc.player.setYaw(camYaw);
            mc.player.setPitch(camPitch);
        }
    }

    private float[] getRotations(Vec3d from, Vec3d to) {
        Vec3d delta = to.subtract(from);
        double distXZ = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        float yaw = (float) Math.toDegrees(Math.atan2(delta.z, delta.x)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.atan2(delta.y, distXZ));
        return new float[]{yaw, pitch};
    }

    private float smooth(float current, float target, float factor) {
        float delta = MathHelper.wrapDegrees(target - current);
        return current + delta * factor;
    }
}
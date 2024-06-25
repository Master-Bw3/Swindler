package mod.master_bw3.swindler.utils

import net.minecraft.util.math.Vec3d
import org.joml.Vector3dc

fun vector_to_vec3d(vector: Vector3dc): Vec3d =
    Vec3d(vector.x(), vector.y(), vector.z())

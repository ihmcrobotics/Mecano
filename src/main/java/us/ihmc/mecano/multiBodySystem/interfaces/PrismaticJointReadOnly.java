package us.ihmc.mecano.multiBodySystem.interfaces;

import us.ihmc.euclid.transform.RigidBodyTransform;

/**
 * Read-only interface for a prismatic joint.
 * <p>
 * A prismatic joint is a joint has 1 degree of freedom of translation.
 * </p>
 * 
 * @author Twan Koolen
 * @author Sylvain Bertrand
 */
public interface PrismaticJointReadOnly extends OneDoFJointReadOnly
{
   /** {@inheritDoc} */
   @Override
   default void getJointConfiguration(RigidBodyTransform jointTransform)
   {
      jointTransform.setRotationToZero();
      jointTransform.setTranslationX(getQ() * getJointAxis().getX());
      jointTransform.setTranslationY(getQ() * getJointAxis().getY());
      jointTransform.setTranslationZ(getQ() * getJointAxis().getZ());
   }
}

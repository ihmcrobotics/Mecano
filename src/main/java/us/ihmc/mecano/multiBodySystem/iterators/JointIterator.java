package us.ihmc.mecano.multiBodySystem.iterators;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import us.ihmc.mecano.multiBodySystem.interfaces.JointReadOnly;
import us.ihmc.mecano.multiBodySystem.interfaces.RigidBodyReadOnly;

/**
 * {@code JointIterator} is a generic iterator that can be used on any implementation of
 * {@code JointReadOnly}.
 * <p>
 * This iterator can be used to iterate through the all the joints of the subtree that starts at
 * {@code root}.
 * </p>
 * 
 * @author Sylvain Bertrand
 *
 * @param <J> the type of the {@code Iterable}.
 */
@SuppressWarnings("unchecked")
public class JointIterator<J extends JointReadOnly> implements Iterator<J>
{
   private final Deque<J> stack = new ArrayDeque<>();
   private final Predicate<J> selectionRule;
   private final Class<J> filteringClass;

   /**
    * Creates a new iterator for multiple subtrees.
    * 
    * @param filteringClass the class of the type of joint to iterate through. If a joint is not an
    *           instance of the {@code filteringClass}, then it will not be part of the iteration.
    * @param selectionRule rule to filter the joints to iterate through. Joints for which
    *           {@code selectionRule.test(joint)} returns {@code false} are ignored and will not be
    *           part of the iteration.
    * @param root joint from which the subtree starts. Not modified.
    */
   public JointIterator(Class<J> filteringClass, Predicate<J> selectionRule, J root)
   {
      this.filteringClass = filteringClass;
      this.selectionRule = selectionRule;
      if (root != null)
         stack.add(root);
   }

   /**
    * Creates a new iterator for multiple subtrees.
    * 
    * @param filteringClass the class of the type of joint to iterate through. If a joint is not an
    *           instance of the {@code filteringClass}, then it will not be part of the iteration.
    * @param selectionRule rule to filter the joints to iterate through. Joints for which
    *           {@code selectionRule.test(joint)} returns {@code false} are ignored and will not be
    *           part of the iteration.
    * @param roots joints from which each subtree starts. Not modified.
    */
   public JointIterator(Class<J> filteringClass, Predicate<J> selectionRule, Collection<? extends JointReadOnly> roots)
   {
      this.filteringClass = filteringClass;
      this.selectionRule = selectionRule;
      if (roots != null)
      {
         for (JointReadOnly root : roots)
         {
            if (filteringClass.isInstance(root))
               stack.add((J) root);
         }
      }
   }

   private J next = null;
   private boolean hasNextHasBeenCalled = false;

   @Override
   public boolean hasNext()
   {
      next = null;

      if (stack.isEmpty())
         return false;

      if (!hasNextHasBeenCalled)
      {
         next = searchNextJointPassingRule();
         hasNextHasBeenCalled = true;
      }
      return next != null;
   }

   @Override
   public J next()
   {
      if (!hasNextHasBeenCalled)
      {
         if (!hasNext())
            throw new NullPointerException();
      }

      hasNextHasBeenCalled = false;
      J ret = next;
      next = null;
      return ret;
   }

   private J searchNextJointPassingRule()
   {
      if (stack.isEmpty())
         return null;

      if (selectionRule == null)
         return searchNextJoint();

      while (!stack.isEmpty())
      {
         J currentJoint = searchNextJoint();
         if (currentJoint == null || selectionRule.test(currentJoint))
            return currentJoint;
      }
      return null;
   }

   private J searchNextJoint()
   {
      if (stack.isEmpty())
         return null;

      J currentJoint = stack.poll();

      RigidBodyReadOnly successor = currentJoint.getSuccessor();

      if (successor != null)
      {
         List<? extends JointReadOnly> childrenJoints = successor.getChildrenJoints();

         if (childrenJoints != null)
         {
            for (JointReadOnly childJoint : childrenJoints)
            {
               if (filteringClass.isInstance(childJoint))
                  stack.add((J) childJoint);
            }
         }
      }

      return currentJoint;
   }
}
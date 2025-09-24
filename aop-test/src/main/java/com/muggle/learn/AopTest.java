package com.muggle.learn;

//package com.muggle.learn;
//
//import com.muggle.learn.annotation.TestQuery;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Type;
//
///**
// * @Description:
// * @Author: muggle
// * @Date: 2020/4/26
// **/
//
//@Aspect
//@Component
//public class AopTest {
//
//    @Pointcut("@annotation(com.muggle.learn.annotation.TestQuery)")
//    public void request() {
//    }
//
//
//    @Around("request()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        TestQuery annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(TestQuery.class);
//        String processor = annotation.processor();
//        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
//        Type type = method.getAnnotatedReturnType().getType();
//        return type.getTypeName();
//    }
//
//}
public class AopTest {

    public static void main(String[] args) {
        final ListNode listNode1 = new ListNode(1);
        final ListNode listNode2 = new ListNode(2);
        final ListNode listNode3 = new ListNode(3);
        final ListNode listNode4 = new ListNode(4);
        listNode1.next=listNode2;
        listNode2.next=listNode3;
        listNode3.next=listNode4;
        final ListNode flip = flip(listNode1);
        System.out.println(flip.val);
        System.out.println(flip.next.next.val);
        System.out.println(19%10);
        int val1=0;
        ListNode listNode=extracted(flip, val1,null);

    }

    private static ListNode extracted(ListNode flip, int val1,ListNode l) {
         ListNode listNode = new ListNode((flip.val + 1+val1) % 10);
        if (l==null){
            l=listNode;
        }else {
            l.next=listNode;
        }
            val1=(flip.val+1)/10;
        if (flip.next!=null){
            extracted(flip.next,val1,listNode);
        }
        return l;
    }

    public static ListNode flip(ListNode li){
        ListNode temp=li.next;
        ListNode result=new ListNode(0);
//        li.next=null;
        while (result!=null){
            result=subFilp(li,temp);
            if (result!=null){
                li=temp;
                temp=result;
            }
        }
        return  temp;
    }
    static ListNode subFilp(ListNode li, ListNode temp) {
        final ListNode next = temp.next;
        temp.next=li;
        return next;
    }
}

class ListNode{
    int val;

    ListNode next;
    ListNode(int val){
        this.val=val;
    }
}
class Solution{



}

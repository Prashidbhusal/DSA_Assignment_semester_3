package questions;

public class week7 {
    static class Node {
        public int key;
        public Node left, right;
    };

    static Node newNode(int key)
    {
        Node temp = new Node();
        temp.key = key;
        temp.left = temp.right = null;
        return temp;
    }



    static int cnt = 0;



    static int CaptureFire(Node root)
    {
        if (root == null)
            return 1;

        int L = CaptureFire(root.left);
        int R = CaptureFire(root.right);

        if (L == 1 && R == 1)
            return 2;

        else if (L == 2 || R == 2) {
            cnt++;
            return 3;
        }



        return 1;
    }

    static void Setup(Node root)
    {
        int value = CaptureFire(root);

        System.out.println(cnt + (value == 2 ? 1 : 0));
    }

    public static void main(String[] args)
    {
        Node root = newNode(0);
        root.left = newNode(0);
        root.left.left = newNode(0);
        root.left.left.left = newNode(0);
        root.left.left.left.right = newNode(0);

        Setup(root);
    }

}

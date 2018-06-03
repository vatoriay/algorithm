import java.util.HashMap;
import java.util.ArrayList;
//二叉树构造
public class BTreeBuilder {
    private HashMap<Integer, Integer> nodeMap = new HashMap<Integer, Integer>();
    private TreeNode root = null;
    private TreeNode node1 = null;
    private TreeNode node2 = null;
    
    public BTreeBuilder(int[] inorder, int[] preorder) {
        for (int i = 0; i < inorder.length; i++) {
            nodeMap.put(inorder[i], i);
        }
        
        buildTree(preorder);
    }
    
    private void buildTree(int[] preorder) {
        if (root == null) {
            root = new TreeNode(preorder[0]);
        }
        
        for (int i = 1; i < preorder.length; i++) {
            int val = preorder[i];
            TreeNode current = root;
            while (true) {
                TreeNode node = null;
                
                if (nodeMap.get(val) < nodeMap.get(current.val)) {
                    if (current.left != null) {
                        current = current.left;
                    } else {
                        node = new TreeNode(val);
                        current.left = node;
                        setNode(node);
                        node.parent = current;
                        break;
                    }
                } else {
                    if (current.right != null) {
                        current = current.right;
                    } else {
                        node = new TreeNode(val);
                        current.right = node;
                        node.parent = current;
                        setNode(node);
                        break;
                    }
                }
                
            }
        }
    }
    
    private void setNode(TreeNode node) {
        if (node != null && node.val == 401) {
            node1 = node;
        }
        
        if (node != null && node.val == 29) {
            node2 = node;
        }
    }
    
    public TreeNode getNode1() {
        return node1;
    }
    
    public TreeNode getNode2() {
        return node2;
    }
    
    public TreeNode getTreeRoot() {
        return root;
    }
}
//要求输入量节点，也就是要查找最近共同祖先的两个节点
public class LowestCommonAscestor {
    private TreeNode node1 = null;
    private TreeNode node2 = null;
    
    public LowestCommonAscestor(TreeNode n1, TreeNode n2) {
        this.node1 = n1;
        this.node2 = n2;
    }
    
    private int findNodeHeight(TreeNode n) {
        int h = 0;
        while (n.parent != null) {
            h++;
            n = n.parent;
        }
        
        return h;
    }
    
    private TreeNode retrackByHeight(TreeNode n,int h) {
        while (n.parent != null && h > 0) {
            h--;
            n = n.parent;
        }
        
        return n;
    }
    
    private TreeNode traceBack(TreeNode n1, TreeNode n2) {
        while (n1 != n2) {
            if (n1 != null) {
                n1 = n1.parent;
            }
            
            if (n2 != null) {
                n2 = n2.parent; 
            }
        }
        
        return n1;
    }
    
    public TreeNode getLCA() {
        int h1 = findNodeHeight(node1);
        int h2 = findNodeHeight(node2);
        
        if (h1 > h2) {
            node1 = retrackByHeight(node1, h1 - h2);
        } else if (h1 < h2){
            node2 = retrackByHeight(node2, h2 - h1);
        }
        
        return traceBack(node1, node2);
    }
}
//代码首先构造了给定二叉树，然后构造一个LowestCommonAscestor对象，并把要查找共同祖先的两个节点提交给它的构造函数
public class BinaryTree {
   public static void main(String[] s) {
       
       int[] inorder = new int[]{28, 271, 0, 6, 561, 17, 3, 314, 2, 401, 641, 1, 257, 7, 278, 29};
       int[] preorder= new int[] {314, 6, 271, 28, 0, 561, 3, 17, 7, 2, 1, 401, 641, 257, 278, 29};
       BTreeBuilder treeBuilder = new BTreeBuilder(inorder, preorder);
       TreeNode n1 = treeBuilder.getNode1();
       TreeNode n2 = treeBuilder.getNode2();
       
       LowestCommonAscestor lca = new LowestCommonAscestor(treeBuilder.getNode1(), treeBuilder.getNode2());
       TreeNode ascester = lca.getLCA();
       
       System.out.println("The lowest common anscestor of node " + n1.val + "," + n2.val + " is " + ascester.val);
   }
}


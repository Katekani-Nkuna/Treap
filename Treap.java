public class Treap<T extends Comparable<T>> {
    public Node<T> root = null;

    @Override
    public String toString() {
        if (root == null) {
            return "";
        }

        return root.toString() + "\n" + toString(root, "");
    }

    private String toString(Node<T> curr, String pre) {
        if (curr == null)
            return "";
        String res = "";

        if (curr.left != null) {
            if (curr.right != null) {
                res += pre + "├(L)─ " + curr.left.toString() + "\n" + toString(curr.left, pre + "|    ");
            } else {
                res += pre + "└(L)─ " + curr.left.toString() + "\n" + toString(curr.left, pre + "     ");
            }
        }

        if (curr.right != null) {
            res += pre + "└(R)─ " + curr.right.toString() + "\n" + toString(curr.right, pre + "   ");
        }
        return res;
    }

    /*
     * Don't change anything above this line
     */

    public void insert(T data) throws DatabaseException {
        root = insert(data, root);
    }

    public Node<T> remove(T data) {
        Node<T> current = root, parent = null;

        /*Find Node*/
        while (current != null){

            if (data.equals(current.data)){
                break;
            }

            parent = current;
            if (current.data.compareTo(data) < 0){
                current = current.right;
            }
            else{
                current = current.left;
            }
        }

        if (current == null){
            return null;
        }

        //Move node to leaf
        while (current.left != null && current.right != null){
            if (current.left.priority > current.right.priority){
                if (parent == null){
                    parent = rotateRight(current);
                    root = parent;
                } else if (parent.left == current) {
                    parent.left = rotateRight(current);
                    parent = parent.left;
                }
                else{
                    parent.right = rotateRight(current);
                    parent = parent.right;
                }
            }
            else{

                if (parent == null){
                    parent = rotateLeft(current);
                    root = parent;
                } else if (parent.left == current) {
                    parent.left = rotateLeft(current);
                    parent = parent.left;
                }
                else{
                    parent.right = rotateLeft(current);
                    parent = parent.right;
                }
            }
        }

        if (parent == null){
            if (current.left != null)
                root = current.left;
            else root = current.right;

            return current;
        }

        if (current.left != null){
            if (parent.left == current)
                parent.left = current.left;
            else
                parent.right = current.left;
        }
        else if (current.right != null){
            if (parent.left == current)
                parent.left = current.right;
            else
                parent.right = current.right;
        }
        else{
            if (parent.left == current)
                parent.left = null;
            else
                parent.right = null;
        }
        return current;
    }

    public Node<T> access(T data) {

        Node<T> temp = find(root, data);

        if (temp == null)
            return temp;

        root = trickleUp(data,root);

        return temp;
    }

    public Node<T> trickleUp (T data, Node<T> current){
        if (current.data.equals(data)){
            current.priority++;
            return current;
        }

        if (current.data.compareTo(data) < 0){
            current.right = trickleUp(data, current.right);

            if (current.right.data.equals(data) && current.right.priority >= current.priority)
                current = rotateLeft(current);
        }
        else{
            current.left = trickleUp(data, current.left);

            if (current.left.data.equals(data) && current.left.priority >= current.priority)
                current = rotateRight(current);
        }

        return current;
    }

    public Node<T> find(Node<T> current, T data){
        if (current == null || current.data.equals(data)){
            return current;
        }

        if (current.data.compareTo(data) < 0){
            return find(current.right, data);
        }
        else{
            return find(current.left,data);
        }
    }

    Node<T> insert(T data, Node<T> current) throws DatabaseException {
        if (current == null){
            current = new Node<>(data);
            return current;
        }

        if (current.data.equals(data)){
            throw DatabaseException.duplicateInsert(data);
        }

        if (current.data.compareTo(data) < 0){
            current.right = insert(data, current.right);

            if (current.right.data.equals(data) && current.right.priority >= current.priority)
                current = rotateLeft(current);
        }
        else{
            current.left = insert(data, current.left);

            if (current.left.data.equals(data) && current.left.priority >= current.priority)
                current = rotateRight(current);
        }

        return current;
    }

    /*Helper Functions*/
    Node<T> rotateLeft(Node<T> P){
        Node<T> Q = P.right;
        Node<T> QLeft = Q.left;

        Q.left = P;
        P.right = QLeft;

        return Q;
    }

    Node<T> rotateRight(Node<T> P) {
        Node<T> Q = P.left;
        Node<T> QRight = Q.right;

        Q.right = P;
        P.left = QRight;

        return Q;
    }

    /*Notes
    * 1. If right child needs to be rotated (Left Rotate)
    * 2. If left child needs to be rotated (Right rotate)
    */

}

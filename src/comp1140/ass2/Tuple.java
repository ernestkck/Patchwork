package comp1140.ass2;

public class Tuple {
    public Object left;
    public Object right;

    public Tuple(Object left, Object right){
        this.left = left;
        this.right = right;
    }

    public Object getLeft() {
        return left;
    }

    public Object getRight() {
        return right;
    }
    public void setLeft(Object left){
        this.left = left;
    }

    public void setRight(Object right) {
        this.right = right;
    }
}

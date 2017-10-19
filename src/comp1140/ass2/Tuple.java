package comp1140.ass2;

public class Tuple {        ////Author: Lachlan Grainger (u5847571)
    private Object[] args;

    public Tuple(Object ... args){
        this.args = args;
    }

    public Object getObjectAt(int pos){
        return args[pos];
    }
    public void   setObjectAt(Object newArg, int pos){
        args[pos] = newArg;
    }

    public Object[] getAll(){
        return args;
    }
    public int length(){
        return args.length;
    }


}

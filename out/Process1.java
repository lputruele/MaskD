public class Process1 implements Runnable { 

  Thread t; 
  boolean nc;
  boolean c;
  boolean w;
  boolean d;

  public Process1() {
    this.c = false;
    this.nc = true;
    this.w = false;
    this.d = false;

  }

  public void run(){
    while (true){
      switch (Program.randomGenerator.nextInt(6)){
        case 0:action0();break;
        case 1:action1();break;
        case 2:action2();break;
        case 3:action3();break;
        case 4:action4();break;
        case 5:action5();break;
        default:break;
      }
    }
  }
  public void start (){
    if (t == null) {
      t = new Thread(this);
      t.start();
    }
  }

  synchronized private boolean action0(){
    if (nc == true && d == false){
      w=true;
      nc=false;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action1(){
    if (w == true && Program.s == false && d == false){
      w=false;
      c=true;
      Program.s=true;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action2(){
    if (c == true && d == false){
      c=false;
      nc=true;
      Program.s=false;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action3(){
    if (d == false){
      d=true;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action4(){
    if (d == true){
      d=true;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action5(){
    if (d == true){
      d=false;
      return true;
    }
    else{
      return false;
    }
  }

}
public class Memory implements Runnable { 

  Thread t; 
  boolean w;
  boolean r;
  boolean c0;
  boolean c1;
  boolean c2;
  boolean c3;
  boolean c4;
  boolean c5;
  boolean c6;

  public Memory() {
    this.w = true;
    this.c0 = true;
    this.c1 = true;
    this.c2 = true;
    this.c3 = true;
    this.c4 = true;
    this.c5 = true;
    this.c6 = true;
    this.r = true;

  }

  public void run(){
    while (true){
      switch (Program.randomGenerator.nextInt(8)){
        case 0:action0();break;
        case 1:action1();break;
        case 2:action2();break;
        case 3:action3();break;
        case 4:action4();break;
        case 5:action5();break;
        case 6:action6();break;
        case 7:action7();break;
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
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action1(){
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action2(){
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action3(){
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action4(){
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action5(){
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action6(){
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action7(){
    if (true){
      return true;
    }
    else{
      return false;
    }
  }

}
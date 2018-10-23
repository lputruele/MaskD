public class OddPhil implements Runnable { 

  Thread t; 
  boolean hasL;
  boolean hasR;
  state s;
  Bool forkL;
  Bool forkR;

  public OddPhil(Bool forkL,Bool forkR) {
    this.s = state.thinking;
    this.hasL = false;
    this.hasR = false;
    this.forkR=forkR;
    this.forkR.setValue(true);
    this.forkL=forkL;
    this.forkL.setValue(true);

  }

  public void run(){
    while (true){
      switch (Program.randomGenerator.nextInt(9)){
        case 0:action0();break;
        case 1:action1();break;
        case 2:action2();break;
        case 3:action3();break;
        case 4:action4();break;
        case 5:action5();break;
        case 6:action6();break;
        case 7:action7();break;
        case 8:action8();break;
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
    if (s == state.thinking){
      s=state.hungry;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action1(){
    if (s == state.thinking){
      s=state.thinking;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action2(){
    if (s == state.hungry && forkL.getValue() && !hasL && !hasR){
      forkL.setValue(false);
      hasL=true;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action3(){
    if (s == state.hungry && !forkL.getValue() && !hasL && !hasR){
      s=state.hungry;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action4(){
    if (s == state.hungry && hasL && forkR.getValue() && !hasR){
      forkR.setValue(false);
      hasR=true;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action5(){
    if (s == state.hungry && hasL && !forkR.getValue() && !hasR){
      forkL.setValue(true);
      hasL=false;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action6(){
    if (s == state.hungry && hasL && hasR){
      s=state.eating;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action7(){
    if (s == state.eating){
      s=state.thinking;
      forkL.setValue(true);
      forkR.setValue(true);
      hasR=false;
      hasL=false;
      return true;
    }
    else{
      return false;
    }
  }

  synchronized private boolean action8(){
    if (true){
      s=state.error;
      return true;
    }
    else{
      return false;
    }
  }

}
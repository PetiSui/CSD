// CSD feb 2013 Juansa Sendra

public class Pool4 extends Pool { //kids cannot enter if there are instructors waiting to exit
    int kids = 0;
    int instructors = 0;    
    int max_kids;
    int max;
    int cap = 0;
    int contador = 0;
    
    public void init(int ki, int cap) {
        max_kids = ki;
        max = cap;
    }

    public synchronized void kidSwims() throws InterruptedException{
        while(instructors < 1 || (kids + 1) > (max_kids * instructors) || cap >= max || contador > 0){
            log.waitingToSwim();
            wait();
        }
             
        log.swimming();
        kids++;  
        cap++;
        //notifyAll();
    }

    public synchronized void kidRests(){        
        log.resting(); 
        kids--;
        cap--;
        notifyAll();
    }

    public synchronized void instructorSwims() throws InterruptedException{
        while(cap >= max){
            wait();
        }
        log.swimming();
        instructors++;
        cap++;
        notifyAll();
    }

    public synchronized void instructorRests() throws InterruptedException{
        while(instructors <= 1 && kids > 0 || kids > (max_kids) * (instructors - 1)){
            contador++;
            log.waitingToRest();
            wait();
            contador--;
        }
        
        instructors--;
        cap--;
        log.resting(); 
        notifyAll();

    }
}

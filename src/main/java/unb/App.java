package unb;

import java.util.ArrayList;
import java.util.List;

import unb.modules.process.ProcessMain;
import unb.modules.process.dtos.Procedure;
import unb.utils.ManagerFile;

/**
 */
public class App {
    public static void main( String[] args ){
		String path = "files/process";
		String nameFile = "schedulling_process_in.txt";
		
        toSchedullerProcess(path, nameFile);
    }
    
    
    public static void toSchedullerProcess(final String path, final String nameFile){
    	List<Procedure> procedures = new ArrayList<Procedure>();
		String text = ManagerFile.readFile(path, nameFile);
		String[] lines = text.split("\n");

		for(int i = 0; i < lines.length; i++) {
			String[] values = lines[i].split(" ");
			
			if(values.length == 2){
				Integer arrivalTime = Integer.valueOf(values[0]);
				Integer durationTime = Integer.valueOf(values[1]);
				
		    	Procedure proc = new Procedure();
		    	proc.setArrivalTime(arrivalTime);
		    	proc.setDurationTime(durationTime);
		    	proc.setId(Long.valueOf(i));
		    	
		    	procedures.add(proc);
				
			}else {
				throw new RuntimeException("O arquivo de entrada não está com o formato de texto correto;");
			}
		}
		
        ProcessMain processManager = new ProcessMain(procedures);
        processManager.toSchedullerProcess();
    }
}

package unb;

import java.util.ArrayList;
import java.util.List;

import unb.modules.inputoutput.InputOutputMain;
import unb.modules.inputoutput.dtos.DiskDriver;
import unb.modules.process.ProcessMain;
import unb.modules.process.dtos.Procedure;
import unb.modules.process.enums.TypeSchedullingAlgorithmEnum;
import unb.utils.ManagerFile;

/**
 */
public class App {
    public static void main( String[] args ){
		String nameFile = "schedulling_process_in.txt";
		String nameFileInputOutput = "requests_in.txt";
		
        toSchedullerProcess("FIFO", nameFile);
        toManageInputOutput(nameFileInputOutput);
    }

	public static void toManageInputOutput(final String nameFile){
		String path = "files/input_output_manager";

		DiskDriver diskDriver = new DiskDriver();
		List<Integer> requests = new ArrayList<>();
		String text = ManagerFile.readFile(path, nameFile);

		String[] lines = text.split("\n");

		for(int i = 0; i < lines.length; i++) {
			String[] values = lines[i].split(" ");
			if(values.length == 1) {
				if (i == 0) {
					diskDriver.setLastCylinder(Integer.parseInt(lines[i]));
				}
				if (i == 1) {
					diskDriver.setcurrentCylinder(Integer.parseInt(lines[i]));
				}
				if (i > 1) {
					requests.add(Integer.parseInt(lines[i]));
				}
			}else {
				throw new RuntimeException("O arquivo de entrada não está com o formato de texto correto;");
			}

		}
		diskDriver.setRequests(requests);

		InputOutputMain ioManager = new InputOutputMain(diskDriver);
		ioManager.toManageIOWithAllAlgorithms();
	}
    
    
    public static void toSchedullerProcess(final String algorithm, final String nameFile){
		String path = "files/process";
		
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
		
		TypeSchedullingAlgorithmEnum typeExec = TypeSchedullingAlgorithmEnum.NON_PREEMPTIVE;
		if(algorithm.contains("PREEMPTIVE")) {
			typeExec = TypeSchedullingAlgorithmEnum.PREEMPTIVE;
		}
		
        ProcessMain processManager = new ProcessMain(procedures);
//        processManager.toSchedullerProcessWithAllAlgorithms();
        processManager.toSchedullerProcess(algorithm, typeExec);
    }
}

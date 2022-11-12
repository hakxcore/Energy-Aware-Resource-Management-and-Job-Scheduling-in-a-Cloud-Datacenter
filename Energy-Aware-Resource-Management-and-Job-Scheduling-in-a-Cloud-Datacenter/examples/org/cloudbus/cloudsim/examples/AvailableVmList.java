
package org.cloudbus.cloudsim.examples;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisioner;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;



public class AvailableVmList {

	
	
	public static void main(String[] args) {
		
		// 1. INITIALIZING CLOUDSIM PACKAGE
		
		int numUser = 1;
		Calendar cal = Calendar.getInstance();
		boolean traceflag=false;
		
		CloudSim.init(numUser, cal, traceflag);
		
		// 2. CREATE DATACENTER
		
		Datacenter dc = createDatacenter();
		
		//3. Create Broker 
		DatacenterBroker dcb =null;
		try {
		dcb = new DatacenterBroker("DatacenterBroker1");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
//		//4. CREATE CLOUDLETS
		List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
		
		//int cloudletid = 0;
		long cloudletLength = 40000;
		int pesNumber = 1;
		long reqDiskSize= 20000;
		long cloudletFileSize = 300;
		long clouletOutputSize = 400;
		UtilizationModelFull fullUtilize = new UtilizationModelFull();
		for (int cloudletid=0;cloudletid<60;cloudletid++) {
			Random r = new Random();
		Cloudlet newcloudlet = new Cloudlet(cloudletid, cloudletLength+r.nextInt(1000), pesNumber,
				cloudletFileSize, clouletOutputSize, fullUtilize, fullUtilize,
				fullUtilize);
		newcloudlet.setUserId(dcb.getId());
		cloudletList.add(newcloudlet);
		}
		
		//5 Create VMs
		/* Adding 30 X-Large VM's 8GB Disk Size, 2GB Ram , XEN with 100mips */
		List<Vm> vmList = new ArrayList<Vm>();
		long size = 8192;
		int ram =2000;
		int mips = 100;
		int bandwidth =1000;
		int vCPU = 1;
		String VMM = "XEN";
		for ( int vmId=0; vmId < 30;vmId++) {
		Vm virtualMachine = new Vm(vmId, dcb.getId(), mips, vCPU, ram, bandwidth, size, VMM, new CloudletSchedulerTimeShared());
		
		vmList.add(virtualMachine);
		}
		
		
		/* Adding 30 X-Large VM's 16GB Disk Size, 4GB Ram , XEN with 400mips */
		long size_m = 16384;
		int ram_m =4000;
		int mips_m = 400;
		int bandwidth_m =1000;
		int vCPU_m = 1;
		String VMM_m = "XEN";
		for ( int vmId=0; vmId < 30;vmId++) {
		Vm virtualMachine = new Vm(vmId, dcb.getId(), mips_m, vCPU_m, ram_m, bandwidth_m, size_m, VMM_m, new CloudletSchedulerTimeShared());
		vmList.add(virtualMachine);
		}
		
		/* Adding 20 Large VM's 16GB Disk Size, 4GB Ram , XEN with 600mips */
		long size_l = 16384;
		int ram_l =4000;
		int mips_l = 600;
		int bandwidth_l =1000;
		int vCPU_l = 1;
		String VMM_l = "XEN";
		for ( int vmId=0; vmId < 20;vmId++) {
		Vm virtualMachine = new Vm(vmId, dcb.getId(), mips_l, vCPU_l, ram_l, bandwidth_l, size_l, VMM_l, new CloudletSchedulerTimeShared());
		vmList.add(virtualMachine);
		}
		
		
		/* Adding 20 X-Large VM's 32GB Disk Size, 8GB Ram , XEN with 1000mips */
		long size_xl = 32768;
		int ram_xl =8000;
		int mips_xl = 1000;
		int bandwidth_xl =1000;
		int vCPU_xl = 1;
		String VMM_xl = "XEN";
		for ( int vmId=0; vmId < 20;vmId++) {
		Vm virtualMachine = new Vm(vmId, dcb.getId(), mips_xl, vCPU_xl, ram_xl, bandwidth_xl, size_xl, VMM_xl, new CloudletSchedulerTimeShared());
		vmList.add(virtualMachine);
		}
		
		dcb.submitCloudletList(cloudletList);
		dcb.submitVmList(vmList);
		
		//6. start simulation
		CloudSim.startSimulation();
		
		List<Cloudlet> finalClouletExecutionResults = dcb.getCloudletReceivedList();		
		CloudSim.stopSimulation();
		String indent = "	";

		/* Created Total 100 VMS
		 * First Adding 30 Small VM's 8GB Disk Size, 2GB Ram , XEN with 100mips
		 * Adding 30 Medium VM's 16GB Disk Size, 4GB Ram , XEN with 400mips
		 * Adding 20 Large VM's 16GB Disk Size, 4GB Ram , XEN with 600mips
		 * Adding 20 X-Large VM's 32GB Disk Size, 8GB Ram , XEN with 1000mips		
			*/	
		int vmNo = 1;
		List<Vm> finalVmList = dcb.getVmList();
		Log.printLine("> Below is the Available VM list" );
		Log.printLine("***********************************************************************************");
		Log.printLine("VM no." +indent+"Unique_VM_id"+indent+"VMM"
		+indent+"VM Id."+indent+"Requested MIPS");
		for(Vm v : finalVmList) {
			Log.printLine( vmNo +indent+"  " +v.getUid() +indent+indent+ v.getVmm() +indent +
					v.getId() + indent + v.getCurrentRequestedTotalMips());
			/**
			 * Get VM number.
			 * Get unique string identificator of the VM.
			 * Get Virtual Machine Manager(VMM) of the VM.
			 * Get Id of the VM
			 * Gets the current allocated SIZE.
			 * Gets the current allocated ram.
			 * Gets the current requested total mips.
			 
			 */
			vmNo++;
		}
		Log.printLine("***********");
		Log.printLine("");
	
	}
	
	
	private static Datacenter createDatacenter() {
		List<Pe>peList = new ArrayList<Pe>();
		PeProvisionerSimple pProvisioner = new PeProvisionerSimple(1000);
		
		Pe core1 = new Pe(0, pProvisioner);
		peList.add(core1);
		Pe core2 = new Pe(1, pProvisioner);
		peList.add(core2);
		Pe core3 = new Pe(2, pProvisioner);
		peList.add(core3);
		Pe core4 = new Pe(3, pProvisioner);
		peList.add(core4);
		
		List<Host> hostlist = new ArrayList<Host>();
		
		int ram =8000;
		int bw = 8000;
		long storage = 100000;
		
		Host host1 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host1);
		Host host2 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host2);
		Host host3 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host3);
		Host host4 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host4);
		Host host5 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host5);
		Host host6 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host6);
		Host host7 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host7);
		Host host8 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host8);
		Host host9 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host9);
		Host host10 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host10);
		Host host11 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host11);
		Host host12 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host12);
		Host host13 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host13);
		Host host14 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host14);
		Host host15 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host15);
		Host host16 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host16);
		Host host17 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host17);
		Host host18 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host18);
		Host host19 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host19);
		Host host20 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host20);
		Host host21 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host21);
		Host host22 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host22);
		Host host23 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host23);
		Host host24 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host24);
		Host host25 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host25);
		Host host26 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host26);
		Host host27 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host27);
		Host host28 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host28);
		Host host29 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host29);
		Host host30 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host30);
		Host host31 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host31);
		Host host32 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host32);
		Host host33 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host33);
		Host host34 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host34);
		Host host35 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host35);
		Host host36 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host36);
		Host host37 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host37);
		Host host38 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host38);
		Host host39 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host39);
		Host host40 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host40);
		Host host41 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host41);
		Host host42 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host42);
		Host host43 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host43);
		Host host44 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host44);
		Host host45 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host45);
		Host host46 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host46);
		Host host47 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host47);
		Host host48 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host48);
		Host host49 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host49);
		Host host50 = new Host(0,new RamProvisionerSimple(ram),new BwProvisionerSimple(bw),storage, peList,new VmSchedulerSpaceShared(peList) );
		hostlist.add(host50);
		
		String architecture= "x86";
		String os ="Linux";
		String vmm = "XEN";
		double timezone = 5.0;
		double ComputecostPerSec = 3.0;
		double costPerMem = 1.0;
		double costPerStorage = 0.05;
		double costPerBw = 0.10;
		
		DatacenterCharacteristics dcCharacteristics = new DatacenterCharacteristics(architecture, os, vmm, hostlist, timezone, ComputecostPerSec, costPerMem,costPerStorage,costPerBw);
		
		LinkedList<Storage> SANstorage = new LinkedList<Storage>();
		Datacenter dc = null;
	    try {
		dc = new Datacenter("Datacenter1",dcCharacteristics,new VmAllocationPolicySimple(hostlist),SANstorage,1);
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	
	    return dc;
	}
}

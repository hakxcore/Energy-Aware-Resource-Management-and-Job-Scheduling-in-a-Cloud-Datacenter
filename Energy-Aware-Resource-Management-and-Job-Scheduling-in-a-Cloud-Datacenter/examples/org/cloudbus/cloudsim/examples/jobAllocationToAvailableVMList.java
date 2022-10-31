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


/*
in this case
----------------One Datacenter
----------------4 Hosts
----------------60 Cloudlets (TASKS)
----------------10 Vm's
*/


public class jobAllocationToAvailableVMList  {

public static void main(String[] args) {

// 1. INITIALIZING CLOUDSIM PACKAGE

int x1=0,numUser = 1;
String Cloudlet_type= null;
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

//4. CREATE CLOUDLETS
List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();

//int cloudletid = 0;

long cloudletLength = 40000;
int pesNumber = 1;
long cloudletFileSize = 300;
long cloudletOutputSize = 400;
UtilizationModelFull fullUtilize = new UtilizationModelFull();
Random r = new Random();
for (int cloudletid=0;cloudletid<60;cloudletid++) {
Cloudlet newcloudlet = new Cloudlet(cloudletid, cloudletLength+r.nextInt(1000), pesNumber,
cloudletFileSize, cloudletOutputSize, fullUtilize, fullUtilize,
fullUtilize);
newcloudlet.setUserId(dcb.getId());
cloudletList.add(newcloudlet);
}


//5 Create VMs

List<Vm> vmList = new ArrayList<Vm>();
long diskSize = 20000;
int ram =2000;
int mips = 1000;
int bandwidth =1000;
int vCPU = 1;
String VMM = "XEN";
for ( int vmId=0; vmId < 10;vmId++) {
Vm virtualMachine = new Vm(vmId, dcb.getId(), mips, vCPU, ram, bandwidth, diskSize, VMM, new CloudletSchedulerTimeShared());
vmList.add(virtualMachine);
}
dcb.submitCloudletList(cloudletList);
dcb.submitVmList(vmList);
//6. start simulation
CloudSim.startSimulation();

List<Cloudlet> finalClouletExecutionResults = dcb.getCloudletReceivedList();

CloudSim.stopSimulation();

//7. PRINT RESULTS AFTER SIMULATION IS OVER ---> OUTPUT

String indent = "    ";
Log.printLine();
Log.printLine("============================================ OUTPUT ============================================");
Log.printLine("Cloudlet ID" + indent + indent+ "Cloudlet Type"+indent+indent+indent+"Allocated to VM" + indent +"Time" +indent+ "Start Time"+ indent + "Finish time" );
Log.printLine("===========" + indent + indent+ "============="+indent+indent+indent+"===============" + indent +"====" +indent+ "=========="+ indent + "===========" );

DecimalFormat dft = new DecimalFormat("###.##");




int cloudletNo = 1;
for(Cloudlet c : finalClouletExecutionResults)
{
 Log.printLine( c.getCloudletId()+indent+indent+indent +indent+
		 x1(Cloudlet_type)+indent+indent+indent+c.getVmId() + 
		 indent+indent+indent +dft.format(c.getActualCPUTime()) 
		 +indent+ dft.format(c.getActualCPUTime())+ indent + 
		 indent+ dft.format(c.getFinishTime()));
}
}
private static String x1(String cloudlet_type) {
	for (int i=0;i<60;i++) {
		Random r1 =new Random();
		int x1 = r1.nextInt(3);
		if(x1==0) {
		cloudlet_type = ("Best Effort         ");
		}
		else if(x1==1) {
		cloudlet_type = ("Immediate Request   ");
		}
		else {
		cloudlet_type = ("Advanced Reservation");
		}
		}	// TODO Auto-generated method stub
	return cloudlet_type;
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

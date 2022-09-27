
package org.cloudbus.cloudsim.examples;

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
import java.util.Random;

/*
 * get the available vm list with their available time
 */

public class getAvailableVMList {



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

//4. CREATE CLOUDLETS
List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();

//int cloudletid = 0;
long cloudletLength = 40000;
int pesNumber = 1;
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
String indent = " ";
//7. PRINT RESULTS AFTER SIMULATION IS OVER ---> OUTPUT
int cloudletNo = 1;
Log.printLine("> Result of completely executed Cloudlets" );
Log.printLine("*******************************");
Log.printLine("Cloudlet-No" +indent + "ID" +indent+ "Allocated-to-VM" + indent + "Status" +indent + "Start" +indent + "Finish"+indent+indent+indent+indent+ "Execution-Time" );
Log.printLine("-----------" +indent + "--" +indent+ "---------------" + indent + "------" +indent + "-----" +indent + "------"+indent+indent+indent+indent+ "--------------" );
for(Cloudlet c : finalClouletExecutionResults)
{

Log.printLine( cloudletNo +indent+indent+ c.getCloudletId()+indent+ c.getVmId()+indent + indent +c.getStatus()+indent + c.getExecStartTime()+indent+ c.getFinishTime()+indent+indent+ c.getActualCPUTime());
// Log.printLine('"%s","%s","%s","%d","%s","%d","%s","%s","%.3f","%s","%.3f","%s","%.3f","%s","%s","%.3f" ',cloudletNo +indent+indent+ c.getCloudletId()+indent+ c.getVmId()+indent + indent +c.getStatus()+indent + c.getExecStartTime()+indent+ c.getFinishTime()+indent+indent+ c.getActualCPUTime());

cloudletNo++;
}
Log.printLine("*******************************");
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

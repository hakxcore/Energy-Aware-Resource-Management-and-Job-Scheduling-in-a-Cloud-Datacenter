# Energy-Aware-Resource-Management-and-Job-Scheduling-in-a-Cloud-Datacenter
Job Scheduler and process simulator in a virtual data center to balance load and establish efficient hardware usage.

## Team Members
* Mehvash Khan (khanmj2@rknec.edu)
* Tejas Bhutada (bhutadats@rknec.edu)
* Mukesh Kumar (kumarm_1@rknec.edu)


## Abstract
In this project we modelled a cloud based datacenter with multiple hosts and VMs running on them with power aware configurations using Cloudsim library in Java. The Aim of this project is to optmise power aware datacenters by varying decisions of VM allocations, segregating cloudlets into categories for scheduling purpose and efficient resource usage while promoting energy awareness throughout the simulating environment. This project is an implementation of a research paper [Energy Aware Resource Management and Job Scheduling in a Cloud Datacenter](https://www.researchgate.net/publication/319403592_Energy_Aware_Resource_Management_and_Job_Scheduling_in_Cloud_Datacenter)

## Introduction
Cloud computing is one of leading technologies which help deliver scalable computations services in a fault-tolerant and sustainable way. In the past decade SaaS, IaaS and PaaS have changed the way large scale computing used to work. Moreover, while some big organizations have been working towards creating cheaper and reliable public cloud offerings, many have contributed towards private and hybrid cloud configurations. Cost of infrastructure and the massive scale of operations make it hard evaluate performance of such platforms.
Simulation studies help fill this gap and with tools like CloudSim. Due to such tools it has become possible to model such environments to optimize different performance, reliability, security and control methodologies. We used [CloudSim](http://www.cloudbus.org/cloudsim/) to generate our power aware datacenter model.

An extensible simulation framework which is generalized in order to enable seamless modelling, simulation and experimentation of emerging cloud computing services and infrastructures. Before actual development of cloud products, controllable methodologies for evaluation of algorithms and policies is required to match the increasing demand of energy efficient IT technologies. By CloudSim, researchers and industry-based developers can take care of system design issues without the concerns of issues related to cloud based infrastructure. So, by utilization of simulation tools, hypothesis can be evaluated prior to software development.

In large data centers energy and resource consumption grows rapidly. Energy and resource aware computing are key challenges of cloud datacenters, which  deal with computing, storage and communication resources. Minimization of energy consumption is achieved through increased resource utilization. Typically, such large scale data centers are concerned with the following four objectives: 

1. Energy usage optimization 
2. Reduction of execution time 
3. Maximum utilization of resources
4. Optimize task scheduling 

## Related Work
Cloud computing is a fast growing area in computing research and industry today. With the advancement of the Cloud, there are new possibilities opening up on how applications can be built on the Internet. On one hand there are the cloud service providers who are willing to provide large scaled computing infrastructure at a cheaper price which is often defined on usage, eliminating the high initial cost of setting up an application deployment environment, and provide the infrastructure services in a very flexible manner which the users can scale up or down at will. On the other hand there are large scaled software systems such as social networking sites and e-commerce applications gaining popularity today which can benefit greatly by using such cloud services to minimize costs and improve service quality to the end users. But when bringing these two ends together there are several factors that will impact the net benefit such as the distribution (geographic) of the user bases, the available Internet infrastructure within those geographic areas, the dynamic nature of the usage patterns of the user base and how well the cloud services can adapt or dynamically reconfigure itself, etc. Doing a comprehensive study on this overall problem in the real world will be extremely difficult, and the best approach to study such a dynamic and massively distributed environment is through simulation.
Over the last few years, energy efficient resource management has extensively been studied. Many of these studies have employed VM consolidation for energy conservation. 

Over the last few years, energy efficient resource management has extensively been studied. Many of these studies have employed VM consolidation for 
energy conservation. The authors in [6] applied limited look ahead control in order to maximize the datacenter profit via energy consumption minimization in work based on VM consolidation. The controller decides the number of physical and virtual machines to be allocated for each request. However, they have not considered how preemption can affect energy consumption where requests have preemptive priority. Authors in [7] consolidate servers using modified best-fit decreasing (MBFD) algorithm in their scheduling. They sort the hosts based on the host utilization and migrate the VM with double threshold method. Authors in [8](##### [8]) argues that VM migration may help to achieve successfully various resource management needs such as load balancing, power management, fault tolerance, and system maintenance. But the migration itself involves practical difficulties such as transfer delay, performance degradation etc which gives rise to lot of overhead and cost. Authors in [9] considered dynamic voltage and frequency scaling (DVFS) and deadline constraint of a job for scheduling. Optimal performance–power ratio of each host is calculated and deadline constraint jobs are given to those VM of a host. Finally consolidation is used for reduce energy where migration is used. This method is not very well suitable for a datacenter which consists of heterogeneous systems.As part of scheduling algorithms, Selvarani [10] proposed an improved cost-based scheduling algorithm for making efficient mapping of jobs to the available resources by grouping them based on capacity in cloud. Jiayin Li [11] proposed a feedback pre-emptible task scheduling algorithm to generate scheduling with the shortest average execution time of jobs. In [12], Yang presented V-heuristics such as V-MCT for job allocation,which allocates every job in an arbitrary order of minimum completion time of the virtualized resource. In this, the completion time of the executing jobs is considered, but not the assigned jobs in the queue. Antony Thomas [13] presented a credit job scheduling in cloud computing by using user priority and task length.

# Objective
The objective of the scheduler is to minimizethe number of servers to save energy. To this end, we propose Energy Aware VM Available Time(EAVMAT) scheduling algorithm. When an AR or IM request comes, CMS will first check the resource availability in one of the active hosts from the list it holds and allocates the request to the hosts. 
If no free availability exists then checks for the earliest available host and assigns to it. If none of the possibility exists then pre-empts the request to
allocate in the existing host itself without switching on the new host. If more workload comes and existing active hosts are not enough then the off state hosts are bring into on state. Since AR/IM jobs can preempt BE request the only scenario where an AR/IM job is rejected is when resources are reserved by other AR jobs at the required time and not enough resources left for the current job in any active host and idle hosts.

# Implementation
We have attempted to minimize the energy consumption by allocating jobs to the VMs of existing active host thereby making unused idle host to get turned off. The challenge here is to be able to identify the servers as idle or near idle so that these can become suitable candidates for turning off. A key consideration that needs to be addressed is how to assign an appropriate job to a suitable VM. 

We present an evaluation for our algorithm in terms of energy benefits and performance. In order to evaluate our algorithm in a large scale set up and calculate energy efficiency thereof, we expanded the CloudSim toolkit to simulate Cloud architecture and perform our experiments.

To this end, we need to understand the type of the request being handled. Based on the user’s need, a job request may be a time (deadline) sensitive or not. Hence we have made use of this time dependency as a key to classify the job request [5] in to three 
different categories. These are:
1. Advance Reservation jobs (AR): Resources are reserved in advance. They must be made available at the specified time. 
2. Immediate jobs (IM): When a client submits a request, based on the resource availability, either the required resources are provisioned immediately, or the request is rejected. 
3. Best effort jobs (BE): These jobs are kept in a queue and resources are provided only when it is available without affecting the execution of the other two types of jobs. It can be batch jobs also.

The Hosts have been classified into three states to regulate energy consumption on each individual host based on their usage and engagement. These are:
1. Active(running) State: TheActive energy state is a high energy state in which the hosts process users' requests and consume a lot of energy.
2. Idle State: It is a state in between the working state and the standby state consuming power for about 70% of the Active State.
3. Standby State: This state consumes about 10% of the energy of the running state.









## References
##### [1]
Q. Zhang, L. Cheng, and R. Boutaba, “Cloud Computing: State-Of-The-Art and Research Challenges”, Journal of internet services and applications, Vol.1, No.1, pp.7-18, 2010

##### [2]
J. Leverich and C. Kozyrakis, “On The Energy (In) Efficiency of Hadoop Clusters”, ACM SIGOPS Operating Systems Review, Vol.44, 
No.1, pp.61-65, 2010.  

##### [3] 
W. Lang and J.M. Patel, “Energy Management for Mapreduce Clusters”, Proceedings of the VLDB Endowment, Vol. 3, No.1-2, pp.129-139, 2010. 

##### [4] 
K.X. Miao and J. He, “Cloud Computing and Open Datacenters”, Intel®Technology Journal, Vol.16, No.4, 2012.

##### [5]
S. Loganathan and S. Mukherjee, “Differentiated Policy Based Job Scheduling with Queue Model and Advanced Reservation Technique in a Private Cloud Environment”, In: Proc. of International Conf. On Grid and Pervasive Computing, pp. 32-39, 2013.

##### [6]
D. Kusic, N. Kandasamy, and G. Jiang, “Combined Power and Performance Management of Virtualized Computing Environments Serving Session-BasedWorkloads”, IEEE Transactions on network and service management, Vol.8, No.3, pp.245-258, 2011. 

##### [7] 
A. Beloglazov, J. Abawajy, and R. Buyya, “Energy-Aware Resource Allocation Heuristics for Efficient Management of Data Centers for Cloud Computing”, Future Generation Computer Systems, Vol.28, No.5, pp.755-768, 2012.

##### [8]
R.W. Ahmad, A. Gani, S.H.A. Hamid, M. Shiraz, A. Yousafzai, and F. Xia, “A Survey on Virtual Machine Migration and Server Consolidation Frameworks for Cloud Data Centers”, Journal of Network and Computer Applications, Vol.52, pp.11-25, 2015.

##### [9]
Y. Ding, X. Qin, L. Liu, and T. Wang, “Energy Efficient Scheduling of Virtual Machines in Cloud with Deadline Constraint”, Future Generation Computer Systems, Vol.50, pp.62-74, 2015.

##### [10] 
S. Selvarani and G.S. Sadhasivam, “Improved Cost-Based Algorithm for Task Scheduling in Cloud Computing”, In: Proc. of International Conf. On Computational intelligence and computing research, pp. 1-5, 2010. 

##### [11] 
J.Li, M. Qiu, J. Niu, W. Gao, Z. Zong, and X.Qin, “Feedback Dynamic Algorithms for Preemptable Job Scheduling in Cloud Systems”,In: Proc. of International Conf. On Web Intelligence and Intelligent Agent Technology, Vol.1, pp. 561-564, 2010. 

##### [12] 
Y. Yang, Y. Zhou, Z. Sun, and H. Cruickshank, “Heuristic Scheduling Algorithms For Allocation Of Virtualized Network And Computing Resources”, Journal of Software Engineering and Applications, Vol.6, No.1, pp.1-13, 2013.

##### [13]
A. Thomas, G. Krishnalal, and V.J. Raj, “Credit Based Scheduling Algorithm in Cloud Computing Environment”, Procedia Computer Science, Vol.46, pp.913-920, 2015.

##### [14]
W. Smith, I. Foster, and V. Taylor, “Schedulling with Advanced Reservations”, In: Proc. of International Conf. On CCGrid, pp. 127 – 132, 2000.

##### [15]
N.R. Kaushik, S.M. Figueira, and S.A. Chiappari, “Flexible Time-Windows for Advance Reservation Scheduling”, In: Proc. of International Conf. On Modeling, Analysis, and Simulation of Computer and Telecommunication Systems, pp. 218-22, 2006.

##### [16]
D.Meisner, B.T. Gold, and T.F. Wenisch, “Powernap: Eliminating Server Idle Power”, ACM Sigplan Notices, Vol.44. No.3, pp. 205-216, 2009.

##### [17]
R.N.Calheiros, R.Ranjan, A. Beloglazov, C.A De Rose, and R. Buyya, “Cloudsim: A Toolkit for Modeling and Simulation of Cloud Computing Environments and Evaluation of Resource Provisioning Algorithms”, Software: Practice and experience, Vol. 41, No.1, pp.23-50, 2011.
##### [18]
B. Sotomayor Basilio, Provisioning Computational Resources Using Virtual Machines And Lease, Report, University of Chicago, 2010.

##### [19]
S. Long, Y. Zhao, and W. Chen, “A Three Phase Energy-Saving Strategy for Cloud Storage Systems”, Journal of Systems and Software, Vol. 87, pp.38-47, 2014.M.A. Salehi, P.R Krishna, K.S. Deepak and R. Buyya, “Preemption-Aware Energy Management in Virtualized Data Centers”, In: Proc. of International Conf. on CLOUD, pp. 844-85, 2012.





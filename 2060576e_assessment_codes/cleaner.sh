#!/bin/sh

#PBS -l walltime=24:00:00
#PBS -l cput=24:00:00
#PBS -l nodes=1:ppn=4:centos6
#PBS -d /export/home/biostuds/2060576e/NGST/assessment
#PBS -m abe
#PBS -M 2060576e@student.gla.ac.uk

for sample in A1 A2 A3 A4 B1 B2 B3 B4 C1 C2 C3 C4

do 
raw=${sample}
trim1=${sample}.trm1.fq
trim2=${sample}.trm2.fq
align=${sample}.sam
#align2=${sample}.bam
align3=${sample}.sorted
sorted=${sample}.sorted.bam
file=${sample}.gtf

# scythe is used to trim the adaptors
# sickle is used to trim low-quality reads (the offset score is 33)
## Reads with a sanger quality score of 28  and below are removed using the -q flag
## Reads with a length below a threhold are removedusinh the -l flag

# hisat is used to align the read to the genome
# samtools are used to convert the sam output of hisat to a bam format
## They also sort the data
## String tie is used to assemble whole transcripts

scythe -a /export/projects/polyomics/biostuds/data/illumina_adapter.fa -o ${trim1} -q sanger ${raw}
sickle se -f ${trim1} -t sanger -o ${trim2} -q 28 -l 50
hisat2 -x /export/projects/polyomics/Genome/Mus_musculus/mm10/Hisat2Index/chr2 -U ${trim2} -q -p 4 --dta --phred33 -S ${align}
samtools view -S -u ${align} | samtools sort - ${align3}
stringtie ${sorted} -G /export/projects/polyomics/Genome/Mus_musculus/mm10/annotations/chr2.gtf -p 4 -o ${file}
done


# Make a list of gtf files
for sample in A1 A2 A3 A4 B1 B2 B3 B4 C1 C2 C3 C4

do 
file=${sample}.gtf
ls ${file} >> gtf_list.txt
done



# merge files in order to generate a non-redundant set of transcripts observed in all the RNA-seq samples
stringtie gtf_list.txt --merge -G /export/projects/polyomics/Genome/Mus_musculus/mm10/annotations/chr2.gtf -o merged_test.gtf


# Reassemble using the merged transcripts as the -G option and create read coverage tables for Ballgown
for sample in A1 A2 A3 A4 B1 B2 B3 B4 C1 C2 C3 C4

do 
sorted=${sample}.sorted.bam
dir=${sample}_stringtie
assembled=/export/home/biostuds/2060576e/NGST/assessment/${dir}/${sample}_new.gtf
abundance=/export/home/biostuds/2060576e/NGST/assessment/${dir}/${sample}gene_abund.tab
coverage=/export/home/biostuds/2060576e/NGST/assessment/${dir}/${sample}cov_refs.gtf

mkdir ${dir}
stringtie ${sorted} -G /export/home/biostuds/2060576e/NGST/assessment/merged_test.gtf  -e -B -C ${coverage} -A ${abundance} -p 4 -o ${assembled}
done

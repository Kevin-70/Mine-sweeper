`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2022/04/28 08:56:50
// Design Name: 
// Module Name: Excute32
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module executs32(Read_data_1,Read_data_2,Sign_extend,Function_opcode,Exe_opcode,ALUOp,
                 Shamt,ALUSrc,I_format,Zero,Jr,Sftmd,ALU_Result,Addr_Result,PC_plus_4
                 );
// from Decoder
input[31:0] Read_data_1; //the source of Ainput
input[31:0] Read_data_2; //one of the sources of Binput
input [31:0] Sign_extend; //one of the sources of Binput,immediate
// from IFetch
input[5:0] Exe_opcode; //instruction[31:26]
input[5:0] Function_opcode; //instructions[5:0]
input[4:0] Shamt; //instruction[10:6], the amount of shift bits
input[31:0] PC_plus_4; //pc+4
// from Controller
input[1:0] ALUOp; //{ (R_format || I_format) , (Branch || nBranch) }
input ALUSrc; // 1 means the 2nd operand is an immediate (except beq,bne）
input I_format; // 1 means I-Type instruction except beq, bne, LW, SW
input Sftmd; // 1 means this is shift
input Jr;//是否要更新ra

output reg[31:0] ALU_Result; // the ALU calculation result
output Zero; // 1 means the ALU_reslut is zero, 0 otherwise
output [31:0] Addr_Result; // the calculated instruction addres
assign Addr_Result=PC_plus_4+(Sign_extend<<2);
//beq0,bne1
wire[31:0] Ainput,Binput; // two operands for calculation
wire[5:0] Exe_code; // use to generate ALU_ctrl. (I_format==0) ? Function_opcode : { 3'b000 , Opcode[2:0] };
wire[2:0] ALU_ctl; // the control signals which affact operation in ALU directely
wire[2:0] Sftm; // identify the types of shift instruction, equals to Function_opcode[2:0]
reg[31:0] Shift_Result; // the result of shift operation
reg[31:0] ALU_output_mux; // the result of arithmetic or logic calculation
wire[32:0] Branch_Addr; // the calculated address of the instruction, Addr_Result is Branch_Addr[31:0]
wire R_format;
assign Branch_Addr=Sign_extend<<2;
assign R_format= Exe_opcode==0;
assign Exe_code= (I_format==0)? Function_opcode:{3'b000,Exe_opcode[2:0]};
assign ALU_ctl[0] = (Exe_code[0] | Exe_code[3]) & ALUOp[1]; 
assign ALU_ctl[1] = ((!Exe_code[2]) | (!ALUOp[1])); 
assign ALU_ctl[2] = (Exe_code[1] & ALUOp[1]) | ALUOp[0];
assign Zero= ALU_output_mux==0?1:0;
//assign Zero= (Ainput==Binput)&&Exe_opcode==6'h4 || (Ainput!=Binput)&&Exe_opcode==6'h5; 

assign Sftm= Function_opcode[2:0];
assign Ainput = Read_data_1; 
wire signed[31:0] Asigned,Bsigned;
wire [31:0]sign_ex;
assign sign_ex=Sign_extend;
assign Binput = (ALUSrc == 0) ? Read_data_2 : Sign_extend;


//reg[31:0] signed_r;
 assign Asigned=Ainput;//绝对值
 assign Bsigned= Binput;//绝对值
always@(ALU_ctl or Ainput or Binput)begin//有符号数和无符号是保存在一起
    case(ALU_ctl)
    3'b000: begin ALU_output_mux=Ainput&Binput;  end//有符号数
    3'b001:ALU_output_mux= Ainput|Binput;
    3'b010:begin  ALU_output_mux= Ainput+Binput;end
    3'b011:begin ALU_output_mux= $unsigned(Ainput)+ $unsigned(Binput) ; end
    3'b100:ALU_output_mux= Ainput^Binput;//addu
    3'b101:ALU_output_mux= ~(Ainput|Binput);
    3'b110:begin ALU_output_mux=  Ainput - Binput; end
    3'b111:ALU_output_mux= $unsigned(Ainput)-$unsigned(Binput); 
    default: ALU_output_mux=32'h0;
    endcase
end
always @* begin // six types of shift instructions
if(Sftmd)begin
case(Sftm[2:0])
3'b000:Shift_Result = Binput << Shamt; //Sll rd,rt,shamt 00000
3'b010:Shift_Result = Binput >> Shamt; //Srl rd,rt,shamt 00010
3'b100:Shift_Result = Binput << Ainput; //Sllv rd,rt,rs 00010
3'b110:Shift_Result = Binput >> Ainput; //Srlv rd,rt,rs 00110
3'b011:begin 
 Shift_Result = $signed(Binput)>>> Shamt; 
end//Sra rd,rt,shamt 00011
3'b111:Shift_Result = $signed(Binput)>>> Ainput; //Srav rd,rt,rs 00111
default:Shift_Result = Binput;
endcase
end
else
Shift_Result = Binput;
end
//
always @(*) begin
if( ((ALU_ctl==3'b111) && (Exe_code[3]==1'b1)) 
||((ALU_ctl==3'b111)&&(I_format==1))||((ALU_ctl==3'b110)&&(I_format==1)) /*to be completed*/ )
begin
if (Exe_code[0]==1'b1)
ALU_Result =  ($unsigned(Ainput) < $unsigned(Binput)) ? 1 : 0;
else 
ALU_Result =  ($signed(Ainput) < $signed(Binput)) ? 1 : 0;
//ALU_output_mux  
//lui operation
end
 if((ALU_ctl==3'b101) && (I_format==1))
ALU_Result<={Sign_extend[15:0],16'b0} ;

//shift operation
else if(Sftmd)
ALU_Result <= Shift_Result ;
//set type operation (slt, slti, sltu, sltiu)can 区分来优化，上板测试时需要优化
//else if(ALU_ctl==3'b101)begin//
//if(Exe_opcode==6'b001011 || Function_opcode==6'b101011&&R_format) //sltiu
//    ALU_output_mux= Ainput-Binput;
//else if(Function_opcode==6'b101010&&R_format || Exe_opcode==6'b001010)//slt,slti
//    ALU_output_mux= Asigned-Bsigned;
//beq,
//if(ALU_output_mux<0)ALU_Result=32'b1;
//else ALU_Result=32'b0;
//other types of operation in ALU (arithmatic or logic calculation)
else 
ALU_Result <= ALU_output_mux;
//Addr_Result<= PC_plus_4+(Sign_extend<<2);
end

endmodule

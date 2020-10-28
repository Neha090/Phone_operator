create table operator(
  operator_id int,
  name varchar2);
/
create table region(
region_id int,
name varchar2(100));
/
create table line_range(
/
create table message(
  msg_from varchar2(10),
  msg_to varchar2(10),
  SENT_TIME VARCHAR2(100 BYTE),
  RECEIVED_TIME	VARCHAR2(100 BYTE),
  DELIVERY_STATUS	VARCHAR2(50 BYTE),
  MESSAGE	VARCHAR2(200 BYTE));
/
create or replace PROCEDURE ADD_OPERATOR(name varchar2)
IS 
BEGIN
 insert into operator values(operator_sequence.nextval,name);
 commit;
END ADD_OPERATOR;
/
create or replace PROCEDURE ADD_REGION(name varchar2 )
IS 
BEGIN
  insert into region values(region_sequence.nextval,name);
  commit;
END ADD_REGION;
/
create or replace PROCEDURE ADD_LINE_RANGE( starts_from varchar2, end_at varchar2,region varchar2,operator varchar2)
IS 
reg number;
op number;
BEGIN
   select region_id into reg from region where name=region;
   select operator_id into op from operator where name=operator;
   insert into line_range values(range_sequence.nextval,starts_from,end_at,reg,op);
   dbms_output.put_line(reg);
   commit;
END ADD_LINE_RANGE;
/
create or replace PROCEDURE add_MESSAGE(msg_from varchar2,msg_to varchar2,message varchar2)
IS 
BEGIN
  insert into message values(msg_from, msg_to,TO_CHAR(SYSDATE, 'DD-MON-YYYY HH:MI:SS'),
  TO_CHAR(SYSDATE, 'DD-MON-YYYY HH:MI:SS'),'delivered', message);
END ;
/
create or replace PROCEDURE GET_MESSAGE_FROM_A_NUMBER(from_number varchar2,data out sys_refcursor)
IS 
msg message.message%type;
BEGIN
    OPEN data FOR SELECT msg_from,message FROM message where msg_from=from_number;
END GET_MESSAGE_FROM_A_NUMBER;
/
create or replace PROCEDURE MESSAGES_RECEIVED(received_number number,result out SYS_REFCURSOR)
IS 
BEGIN
 open result for 
 select msg_to,message from message where msg_to=received_number;
END MESSAGES_RECEIVED;
/
create or replace PROCEDURE MESSAGES_FROM_TO 
(
  FROM_NUMBER IN VARCHAR2 ,
  TO_NUMBER IN VARCHAR2 ,
  result out sys_refcursor
) 
IS 
BEGIN
  open result for 
  select msg_from,msg_to,message from message where msg_from=from_number and msg_to=TO_NUMBER;
END MESSAGES_FROM_TO;
/
create or replace PROCEDURE GET_MESSAGE_FROM_STATE(reg varchar2,data out sys_refcursor) IS 
BEGIN
 open data for
  select m.msg_from,r.name,m.message
  from message m ,line_range lr inner join region r on lr.region_id=r.region_id 
  inner join operator op on lr.operator_id=op.operator_id 
  where r.name=reg and
  substr(m.msg_from,1,5) >= lr.starts_from
  and substr(m.msg_from,1,5) <= lr.end_at;
END GET_MESSAGE_FROM_STATE;
/
create or replace PROCEDURE get_message(reg varchar2,op varchar2,data out sys_refcursor) IS  
BEGIN
  open data for
  select op.name,r.name,m.msg_from,m.message
  from message m ,line_range lr inner join region r on lr.region_id=r.region_id 
  inner join operator op on lr.operator_id=op.operator_id 
  where r.name=reg and 
  op.name=op and
  substr(m.msg_from,1,5) >= lr.starts_from
  and substr(m.msg_from,1,5) <= lr.end_at;
END;
/
create or replace PROCEDURE INCOMPLETE_NUMBER(ph varchar2,result out sys_refcursor)
IS 
BEGIN
  open result for 
  select msg_from,message from message where msg_from like ph||'%' ;
END INCOMPLETE_NUMBER;
/
create or replace PROCEDURE NOT_DELIVERED(reg varchar2,result out sys_refcursor) IS 
BEGIN
    open result for 
    select r.name,m.msg_from, m.message,m.delivery_status 
    from message m ,line_range lr inner join region r on lr.region_id=r.region_id 
    inner join operator op on lr.operator_id=op.operator_id 
    where r.name=reg and m.delivery_status='Undelivered' and
    substr(m.msg_from,1,5) >= lr.starts_from
    and substr(m.msg_from,1,5) <= lr.end_at;
END NOT_DELIVERED;
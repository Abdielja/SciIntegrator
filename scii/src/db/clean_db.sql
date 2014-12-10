delete from up_product;
delete from up_payment_detail;
delete from up_cash_detail;
delete from up_data;

delete from transaction;
delete from transaction_object;
delete from transaction_error;
delete from user_data;
delete from customers;
delete from customer_extra;
delete from product;
delete from products;
delete from invoice_line;
delete from invoice;
delete from invoices;
delete from payment;
delete from payments;
delete from quotation;
delete from sales_order_line;
delete from sales_order;
delete from orders;
delete from incidence;
delete from log;


update user_scii set status=0;

commit;

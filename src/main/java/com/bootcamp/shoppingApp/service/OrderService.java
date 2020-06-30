package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.enums.FromStatus;
import com.bootcamp.shoppingApp.Model.enums.ToStatus;
import com.bootcamp.shoppingApp.Model.orderPack.OrderProduct;
import com.bootcamp.shoppingApp.Model.orderPack.OrderStatus;
import com.bootcamp.shoppingApp.Model.orderPack.OrderT;
import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import com.bootcamp.shoppingApp.Model.user.Customer;
import com.bootcamp.shoppingApp.Model.utilPack.AuditingInfo;
import com.bootcamp.shoppingApp.dto.OrderDto;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CustomerRepo;
import com.bootcamp.shoppingApp.repository.OrderProductRepo;
import com.bootcamp.shoppingApp.repository.OrderRepository;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
public class OrderService {

    @Autowired
    private OrderProductRepo orderProductRepo;
    @Autowired
    private ProductVariationRepo productVariation;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserEmailFromToken userEmailFromToken;

    public String placeOrder(OrderDto orderDto, HttpServletRequest request){
        Customer customer=customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        OrderT orderT=new OrderT();
        orderT.setAmountPaid(orderDto.getAmountPaid());
        orderT.setPaymentMethod(orderDto.getPaymentMethod());
        orderT.setCustomer(customer);
        orderT.setCustomerAddressAddressLine(orderDto.getCustomerAddressAddressLine());
        orderT.setCustomerAddressCity(orderDto.getCustomerAddressCity());
        orderT.setCustomerAddressCountry(orderDto.getCustomerAddressCountry());
        orderT.setCustomerAddressState(orderDto.getCustomerAddressState());
        orderT.setCustomerAddressLabel(orderDto.getCustomerAddressLabel());
        orderT.setCustomerAddressZipCode(orderDto.getCustomerAddressZipCode());
        AuditingInfo auditingInfo=new AuditingInfo();
        auditingInfo.setCreatedBy(customer.getFirstName());
        auditingInfo.setDateCreated(orderDto.getDate());
        auditingInfo.setLastUpdated(orderDto.getDate());
        auditingInfo.setUpdatedBy(customer.getFirstName());
        orderT.setAuditingInfo(auditingInfo);

        Set<OrderProduct> orderProducts=new HashSet<>();
        orderDto.getOrderProductDtoSet().forEach(orderProductDto ->{
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setPrice(orderProductDto.getPrice());
            orderProduct.setQuantity(orderProductDto.getQuantity());
            orderProduct.setProductVariation(productVariation.findById(orderProductDto.getProductVariationId()).get());
            orderProduct.setProductVariationMetadata(orderProductDto.getMetadata());
            orderProduct.setOrderT(orderT);
            OrderStatus orderStatus=new OrderStatus();
            orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
            orderStatus.setToStatus(ToStatus.ORDER_CONFIRMED);
            orderStatus.setTransitionNotesComments("my order");
            orderStatus.setOrderProduct(orderProduct);
            Set<OrderStatus> orderStatuses=new HashSet<>();
            orderStatuses.add(orderStatus);
            orderProduct.setOrderStatuses(orderStatuses);
            orderProducts.add(orderProduct);
            orderT.setOrderProducts(orderProducts);



            orderRepository.save(orderT);

        } );

        return "Success";
    }
    public List<?> customerOrders(HttpServletRequest request){
        Customer customer=customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        List<OrderT> orderTS=orderRepository.findByCustomer(customer);
        return orderTS;
    }
    public void cancelOrder(Long id){
        Optional<OrderProduct> orderProduct=orderProductRepo.findById(id);
        if(!orderProduct.isPresent()){
            throw new ResourceNotFoundException("order"+id+"not found");

        }

        orderProduct.get().getOrderStatuses().forEach(orderStatus -> {
            if(orderStatus.getOrderProduct().getId().compareTo(id)==0)
                statusChange(orderProduct,ToStatus.CANCELLED);
        });
        orderProductRepo.save(orderProduct.get());

    }

    public void returnOrder(Long id){
        Optional<OrderProduct> orderProduct=orderProductRepo.findById(id);
        if(!orderProduct.isPresent()){
            throw new ResourceNotFoundException("order " +id+" not found");

        }
        orderProduct.get().getOrderStatuses().forEach(orderStatus -> {
            statusChange(orderProduct,ToStatus.RETURN_APPROVED);
        });
        orderProductRepo.save(orderProduct.get());
    }


    private Optional<OrderProduct> statusChange(Optional<OrderProduct>orderProduct,ToStatus toStatus){
        OrderStatus orderStatus=new OrderStatus();
        orderStatus.setFromStatus(FromStatus.ORDER_CONFIRMED);
        orderStatus.setToStatus(toStatus);
        orderStatus.setTransitionNotesComments("order"+toStatus.toString());
        orderStatus.setOrderProduct(orderProduct.get());
        Set<OrderStatus> orderStatuses=orderProduct.get().getOrderStatuses();
        orderStatuses.add(orderStatus);
        orderProduct.get().setOrderStatuses(orderStatuses);
        orderProduct.get().getOrderT().getAuditingInfo().setLastUpdated(new Date());
        orderProduct.get().getOrderT().getAuditingInfo().setUpdatedBy(orderProduct.get().getOrderT().getCustomer().getFirstName());
        return orderProduct;
    }


}

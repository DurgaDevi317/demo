# demo
Movie Ticket Booking System

Technology stack for Ticket Booking System
-------------------------------------------
 FRONT END - React Js
 BACK END  - JAVA & Spring Boot
 DATABASE  - Postgresql
 
Project Flow
-------------
 1. Add User Details 
     Mobile Number and Name of the user will be added
 2. Showing seats 
     Booked Seats - User can't able to select these seats.
     Available seats - User can able to select these seats only.
     The User can able to select only 6 seats maximum.
 3. Navigate to payment section
     Once the user navigates to payment section, there will be 2 minutes to complete his transaction.
     The User can able to select only 6 seats maximum.
 4. On successful Payment
     Seats will be booked for the user.
 5. On Failed Payment/ Takes time more than 2 mintues
     Seats selected by the user will be released and those seats can able to select by the other users.
 
 Handling Multiple Requests
 --------------------------
  Spring boot creates separate threads for each requests and synchornized is used to handle multiple requests. Priority thread scheduling can also be used to achieve this scenario.
 
 Future Implementation
 ----------------------
  As for now, Timer has been used for task scheduling. ScheduleExecutor can also be used to achieve the scenario efficiently. I am working on it.

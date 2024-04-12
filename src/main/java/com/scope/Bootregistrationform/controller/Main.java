
package com.scope.Bootregistrationform.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.scope.Bootregistrationform.model.City;
import com.scope.Bootregistrationform.model.Country;
import com.scope.Bootregistrationform.model.Course;
import com.scope.Bootregistrationform.model.State;
import com.scope.Bootregistrationform.model.Users;
import com.scope.Bootregistrationform.model.contactmodel;
import com.scope.Bootregistrationform.repository.countryRepository;
import com.scope.Bootregistrationform.repository.courseRepository;
import com.scope.Bootregistrationform.repository.userRepository;
import com.scope.Bootregistrationform.repository.userRepository2;
import com.scope.Bootregistrationform.services.CityService;
import com.scope.Bootregistrationform.services.CountryService;
import com.scope.Bootregistrationform.services.Services;
import com.scope.Bootregistrationform.services.StateService;

import jakarta.mail.MessagingException;
import jakarta.mail.Service;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller 
public class Main {

    @Autowired
    private Services serve;
    
    @Autowired	
    private userRepository2 repo;
    
    @Autowired 
    private CountryService countryserve;
    
    @Autowired
    private StateService stateserve;
    
    @Autowired
    private CityService cityserve;
    
    @Autowired
    private courseRepository courseRepo;
    
    
    @GetMapping("/home")
    public String homePage(Model model) {
    	model.addAttribute("usercontact", new contactmodel()); 
        return "Home";
    }

    @RequestMapping("/form")
    public String form(Model model) { 
    	 List<Country> countryList = countryserve.countrylist();
         model.addAttribute("countryList", countryList);
        model.addAttribute("user", new Users());
        
        return "Registration";
    }
     
    @GetMapping("/states/{id}")
    public @ResponseBody Iterable<State> getstateByCountry(@PathVariable Country id){
    
    	 return stateserve.getstateBy(id);
    }
    
    @GetMapping("/cities/{stateid}")
    public @ResponseBody Iterable<City>getcityeByState(@PathVariable State stateid){
    	 return cityserve.getcityBy(stateid);
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") Users user, BindingResult result,
            @RequestParam("avatar") MultipartFile file, Model model, HttpServletRequest request)
            throws IOException, MessagingException {


        if (!file.isEmpty()) {  
            try {
                user.setAvatar(file.getBytes());
                serve.Insert(user, getSiteURL(request));
                model.addAttribute("user", new Users());
                return "Registration"; 
            } catch (Exception e) { 
                e.printStackTrace();
            } 
        } else {
        	model.addAttribute("errorMessage", "error 2");
            return "Empty";
        }
        model.addAttribute("errorMessage", "error 3");
        return "Empty";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");
    }

    @RequestMapping("/verify")
    public String verify(@RequestParam("code") String verificationCode, Model model) {
        try {
            if (serve.verify(verificationCode)) {
            	 model.addAttribute("Data", new Users());
                return "send-otp"; 
            } else {
                return "Empty";
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return "Empty";
        }
    }
     
    @RequestMapping("/Dashboard")
    public String studentdashboard(Model model,HttpSession session,
    		HttpServletResponse response) {
    	String email = (String) session.getAttribute("loggedEmail");
    	 Users user = repo.findByEmail(email);
         
         if (user != null) {
           
        	  
             String firstname =user.getFirstname();
     		String lastname =user.getLastname();
     		String FullName =firstname+" "+lastname;
             String Email = user.getEmail();
             String Gender = user.getGender();
             Date dob=user.getDateofbirth();
             Country country = user.getCountry();
             State state = user.getState();
             City city = user.getCity();
             String Hobbie =user.getHobbie();
             byte[] avatar = user.getAvatar();
             String base64Image = Base64.getEncoder().encodeToString(avatar);
             

     		model.addAttribute("name",FullName);
     		model.addAttribute("email",Email);
     		model.addAttribute("gender",Gender);
     		model.addAttribute("Avatar",base64Image);
     		 model.addAttribute("Hobbies",Hobbie);
     		 model.addAttribute("dob",dob);
     		 model.addAttribute("country",country);
     		 model.addAttribute("state",state);
     		 model.addAttribute("city",city);
     		  
             
             return "dashboard";
         } else {
             return "redirect:/login";  
         }
    }
    
    @PostMapping("/send-otp")
    public String SendOtp(Model model,@RequestParam("email")String email, Users user) {
   	   Users userexist = repo.findByEmail(email);
   			   
    	if (userexist != null) {
            String newOtp = generateRandomOtp();
            userexist.setVerified(false);
            userexist.setOtp(newOtp); 
            repo.save(userexist); 
            serve.sendEmail(email, newOtp); 
            model.addAttribute("user",new Users());      
            return "verify-otp"; 
        } else {
            model.addAttribute("errorMessage", "User not found with the provided email address");
            return "Empty"; 
        }  
    }
    
    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam("email") String email,@RequestParam("otp") String enteredOTP,Model model) {
    	Users user = repo.findByEmail(email);
    	if(user !=null && user.getOtp()!=null && user.getOtp().equals(enteredOTP)) {
    		user.setVerified(true);
    		repo.save(user);
    		model.addAttribute("user",new Users());
    		model.addAttribute("email",email);
    		return "set-new-password";
    		
    	}else { 
    	
    		return "verify-otp";     
    		}
    }
    
    private String generateRandomOtp() {
    	String otp = String.valueOf(new Random().nextInt(900000)+ 100000);
    	return otp;
    }
    
    
   
   
       // @PostMapping("/set-new-password")
    @RequestMapping(value = "/set-new-password", method = {RequestMethod.GET, RequestMethod.POST})
        public String setNewPassword(@RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     Model model) {
            if (!password.equals(confirmPassword)) { 
                
                model.addAttribute("errorMessage", "Passwords do not match"); 
                return "Empty"; 
            }else {
            	 model.addAttribute("user",new Users());
            	 serve.updatePassword(email, password); 
            	return "login";
            }
         
        }
    

     

    @GetMapping("/login")
    public String log( Model model) {
    	model.addAttribute("user",new Users()) ;
    	return "login";
    }
    
    
    
    @RequestMapping("/newuser")
    public String newUser( Model model) {
    	List<Country> countryList = countryserve.countrylist();
        model.addAttribute("countryList", countryList);
       model.addAttribute("user", new Users());
       
       return "Registration"; 
    }
    
    
        
        
        @PostMapping("/login-process")
       
        public String loginpage(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model,
                               HttpSession session,HttpServletResponse response) {

//            String loggedEmail = (String) session.getAttribute("loggedEmail");
//            if (loggedEmail != null) {
//                // User is already logged in, redirect to dashboard
//                return "dashboard";
//            }
        	
            Users user = repo.findByEmail(email);
            
            if (user != null && user.getPassword().equals(password) && user.isVerified()) {
                session.setAttribute("loggedEmail", user.getEmail()); 
                session.setMaxInactiveInterval(3600);
//                Cookie cookie = new Cookie("loggedEmail", user.getEmail());
//                cookie.setMaxAge(3600); // Cookie lasts for 1 hour (you can adjust this)
//                response.addCookie(cookie);
                
                String firstname =user.getFirstname();
        		String lastname =user.getLastname();
        		String FullName =firstname+" "+lastname;
                String Email = user.getEmail();
                String Gender = user.getGender();
                Date dob=user.getDateofbirth();
                Country country = user.getCountry();
                State state = user.getState();
                City city = user.getCity();
                String Hobbie =user.getHobbie();
                byte[] avatar = user.getAvatar();
                String base64Image = Base64.getEncoder().encodeToString(avatar);
                

        		model.addAttribute("name",FullName);
        		model.addAttribute("email",Email);
        		model.addAttribute("gender",Gender);
        		model.addAttribute("Avatar",base64Image);
        		 model.addAttribute("Hobbies",Hobbie);
        		 model.addAttribute("dob",dob);
        		 model.addAttribute("country",country);
        		 model.addAttribute("state",state);
        		 model.addAttribute("city",city);
        		  
                
                return "dashboard";
            } else {
            	model.addAttribute("user",new Users());
            	model.addAttribute("sucess","Please provide valid email and password");           	
            	return "login";  
            }
        }
    
    
 
 
        @RequestMapping("/forgot-password")
        public String passwordchange(Model model) {
        	model.addAttribute("user", new Users())	;
        	
        	
        	return "passwordchangepage";
        	
        	
        }
        
        @PostMapping("/reset-password")
        public String resetpassword(@ModelAttribute("user") Users user) {
        	
      Users use =serve.getuserbyemail(user.getEmail());
      use.setPassword(user.getPassword());
        	serve.updatepassword(use);
        	
        	 
        	return "redirect:/login";
        	
        	
        }
         
         

        
   

        @RequestMapping(value = "/edit-details", method = {RequestMethod.GET, RequestMethod.POST})
        public String editDetails(Model model, HttpSession session) {
            // Retrieve user from session
            String loggedUser = (String) session.getAttribute("loggedEmail");

            if (loggedUser == null) {
                // If the user is not logged in, redirect to the login page
                return "Empty";
            } 

            // Add the user to the model
            Users user = serve.getuserbyemail(loggedUser);
            model.addAttribute("user", user); 

             
           // String Hobbie =user.getHobbie();
           // model.addAttribute("Hobbies",Hobbie);
            // Add country, state, and city data to the model
            Country country = user.getCountry();
           State state = user.getState();
           City city = user.getCity();
            model.addAttribute("state", state);
            model.addAttribute("country", country);           
            model.addAttribute("city", city);

            // Load lists of countries, states, and cities
            List<Country> countryList = countryserve.countrylist();
            model.addAttribute("countryList", countryList);

            if (country != null) {
                List<State> stateList = stateserve.getstateBy(country);
                model.addAttribute("state", stateList);

                if (state != null) {
                    List<City> cityList = cityserve.getcityBy(state);
                    model.addAttribute("city", cityList);
                }
            }

            
            byte[] avatar = user.getAvatar();
            if (avatar != null) {
                String base64Image = Base64.getEncoder().encodeToString(avatar);
                model.addAttribute("Avatar", base64Image);
            }

            return "edit-dashboard";
        }

        
        
      
        @RequestMapping(value = "/update-details", method = {RequestMethod.GET, RequestMethod.POST})
        public String updateDetails( @Valid @ModelAttribute("user") Users user, BindingResult result,
        		@RequestParam("avatar") MultipartFile file,
        		Model model, HttpSession session) throws IOException {
           
        	
                     
            user.setAvatar(file.getBytes());
               
            serve.updateUserdetails(user);
          
            model.addAttribute("user", user);

            model.addAttribute("message", "User details updated successfully");
            String firstname =user.getFirstname();
    		String lastname =user.getLastname();
    		String FullName =firstname+" "+lastname;
            String Email = user.getEmail();
            String Gender = user.getGender();
            Date dob=user.getDateofbirth();
            Country country = user.getCountry();
            State state = user.getState();
            City city = user.getCity();
            String Hobbie =user.getHobbie();
            byte[] avatar = user.getAvatar();
           // Integer Course=user.getJoinedcourse();
            String base64Image = Base64.getEncoder().encodeToString(avatar);
            
//            Integer selectedCourse = user.getJoinedcourse();
//            model.addAttribute("selectedCourse", selectedCourse);
            

    		model.addAttribute("name",FullName);
    		model.addAttribute("email",Email);
    		model.addAttribute("gender",Gender);
   		    model.addAttribute("Avatar",base64Image);
   
    	    model.addAttribute("dob",dob);
    		model.addAttribute("country",country);
    		model.addAttribute("state",state);
    		model.addAttribute("city",city);
    		model.addAttribute("Hobbies",Hobbie);
    		//model.addAttribute("course",Course);
    		 
             
            return "dashboard"; 
        }

        
      

        @GetMapping("/course")
        public String coursenames( Model model) {
        	List<Course> courses = courseRepo.findAll();
        	model.addAttribute("courses", courses);
			return "course-names";
        	
        }
        
        @GetMapping("/course-details/{courseid}")
        public String courseDetailsPage(@PathVariable String courseid, Model model, HttpSession session) {
            try {
                int courseId = Integer.parseInt(courseid);
                Course course = courseRepo.findById(courseId);
                if (course != null) {
                    model.addAttribute("course", course); 

                    // Retrieve email from session
                    String userEmail = (String) session.getAttribute("loggedEmail");

                    // Get the currently logged-in user using the email
                    Users currentUser = serve.getCurrentUser(userEmail);
                    if (currentUser != null) {
                        model.addAttribute("email", currentUser);
                    }
                    model.addAttribute("usercontact", new contactmodel()); 
                    return "course-details";
                } else {
                	model.addAttribute("message", "the current user is null");
                    return "Empty"; 
                }
            } catch (NumberFormatException e) {
            	model.addAttribute("message", " if courseid is not a valid integer");
                return "Empty"; 
            }
        }

        @GetMapping("/saveid/{courseid}")
        public String saveCourseId(@PathVariable(name = "courseid", required = false) String courseIdString,
                                   @RequestParam("email") String email,
                                   Model model, HttpSession session) {

            // Initialize courseId to null
            Integer courseId = null;

            // Parse courseIdString to an integer if it's not null
            if (courseIdString != null) {
                try {
                    courseId = Integer.parseInt(courseIdString);
                } catch (NumberFormatException e) {
                    // Handle the case when courseIdString cannot be parsed to an integer
                    // Log the error or perform appropriate error handling
                    return "error";
                }
            }

            // Check if courseId is not null
            if (courseId != null) {
                // Get the user by email
                Users user = serve.getCurrentUser(email);

                if (user != null) {
                    // Set the joined course for the user
                    user.setJoinedcourse(courseId);  
                    
                    // Save the updated user
                    serve.saveUser(user);
                    model.addAttribute("message", "!!You have been successfully enrolled to the course!!");
                    List<Course> courses = courseRepo.findAll();
                	model.addAttribute("courses", courses);
                    return "course-names";
                   
                } else {
                    
                	model.addAttribute("user",new Users()) ;
                	return "login";
                }
            }

            // If courseId is null or if any other error occurs, add an error message to the model
            model.addAttribute("message", "Some error occurred");
            
            // Redirect to a page indicating the error
            return "Empty";
        }

        
        @GetMapping("/contacts")
        public String showContactForm(Model model) {
            model.addAttribute("usercontact", new contactmodel()); 
            return "contact"; 
        } 
  
          
        @PostMapping("/sendmail")  
        public String sendMail(contactmodel contact, Model model, Users user) {
            String toAddress = "manuadwayth@gmail.com";
            String fromAddress = user.getEmail();
            String name = contact.getName(); 
            String number = contact.getNumber();
            String message = contact.getMessage();
            
            
            String emailContent = "Name: " + name + "\nNumber: " + number + "\nMessage: " + message;
            
            // Send email
            serve.sendingmail(toAddress, fromAddress, "New Contact Form Submission", emailContent);

          
           
           
            model.addAttribute("usercontact", new contactmodel());
           // model.addAttribute("sucess", "Your message has been sent successfully!");
            return "contact";
        }
    
        @GetMapping("/logout")
        public String logout(HttpSession session, HttpServletResponse response) {
            // Invalidate session
            session.invalidate();
            
            // Remove cookie
            Cookie cookie = new Cookie("loggedEmail", "");
            cookie.setMaxAge(0); // Set the cookie to expire immediately
            response.addCookie(cookie);
            
            return "redirect:/home "; // Redirect to the login page
        }

}
       



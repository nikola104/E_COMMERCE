package eCommerce.com.eCommerce.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import eCommerce.com.eCommerce.dto.request.AuthenticationRequest;
import eCommerce.com.eCommerce.dto.request.RegistrationRequest;
import eCommerce.com.eCommerce.dto.response.AuthenticationResponse;
import eCommerce.com.eCommerce.exception.InvalidCredentialsException;
import eCommerce.com.eCommerce.model.ConfirmationToken;
import eCommerce.com.eCommerce.model.User;
import eCommerce.com.eCommerce.service.ConfirmationTokenService;
import eCommerce.com.eCommerce.service.EmailService;
import eCommerce.com.eCommerce.service.ShoppingCartService;
import eCommerce.com.eCommerce.service.UserService;
import eCommerce.com.eCommerce.service.impl.JWTServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserService userService;
    private final JWTServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final static Logger LOGGER = LoggerFactory.
            getLogger(AuthenticationService.class);



    public String register(RegistrationRequest request) {
        //check if email exists
        userService.existsByEmail(request.getEmail());


        //password does not match
        if(!(request.getPassword().equals(request.getConfirmPassword()))){
            LOGGER.info("Password does not match!");
            throw new InvalidCredentialsException("Password does not match!");

        }

            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .isVerified(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            userService.save(user);

         //creating confirmation token
        String registrationToken = UUID.randomUUID().toString();
        var confirmationToken = ConfirmationToken.builder()
                .token(registrationToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

             confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + registrationToken;
        emailService.send(user.getEmail(),
                buildEmail(user.getFirstName(),link),"Houseland ltd Email Confirmation");


            return "Successfully registration!";
    }


    private String buildEmail(String firstName, String link) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n" +
                " <head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "  <title>New Template</title><!--[if (mso 16)]>\n" +
                "    <style type=\"text/css\">\n" +
                "    a {text-decoration: none;}\n" +
                "    </style>\n" +
                "    <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "    <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG></o:AllowPNG>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "    </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]--><!--[if !mso]><!-- -->\n" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Poppins&display=swap\" rel=\"stylesheet\"><!--<![endif]-->\n" +
                "  <style type=\"text/css\">\n" +
                ".rollover:hover .rollover-first {\n" +
                "  max-height:0px!important;\n" +
                "  display:none!important;\n" +
                "  }\n" +
                "  .rollover:hover .rollover-second {\n" +
                "  max-height:none!important;\n" +
                "  display:block!important;\n" +
                "  }\n" +
                "  .rollover span {\n" +
                "  font-size:0px;\n" +
                "  }\n" +
                "  u + .body img ~ div div {\n" +
                "  display:none;\n" +
                "  }\n" +
                "  #outlook a {\n" +
                "  padding:0;\n" +
                "  }\n" +
                "  span.MsoHyperlink,\n" +
                "span.MsoHyperlinkFollowed {\n" +
                "  color:inherit;\n" +
                "  mso-style-priority:99;\n" +
                "  }\n" +
                "  a.es-button {\n" +
                "  mso-style-priority:100!important;\n" +
                "  text-decoration:none!important;\n" +
                "  }\n" +
                "  a[x-apple-data-detectors] {\n" +
                "  color:inherit!important;\n" +
                "  text-decoration:none!important;\n" +
                "  font-size:inherit!important;\n" +
                "  font-family:inherit!important;\n" +
                "  font-weight:inherit!important;\n" +
                "  line-height:inherit!important;\n" +
                "  }\n" +
                "  .es-desk-hidden {\n" +
                "  display:none;\n" +
                "  float:left;\n" +
                "  overflow:hidden;\n" +
                "  width:0;\n" +
                "  max-height:0;\n" +
                "  line-height:0;\n" +
                "  mso-hide:all;\n" +
                "  }\n" +
                "  .es-button-border:hover > a.es-button {\n" +
                "  color:#ffffff!important;\n" +
                "  }\n" +
                "@media only screen and (max-width:600px) {.es-m-p0r { padding-right:0px!important } *[class=\"gmail-fix\"] { display:none!important } p, a { line-height:150%!important } h1, h1 a { line-height:120%!important } h2, h2 a { line-height:120%!important } h3, h3 a { line-height:120%!important } h4, h4 a { line-height:120%!important } h5, h5 a { line-height:120%!important } h6, h6 a { line-height:120%!important } h1 { font-size:30px!important; text-align:center } h2 { font-size:24px!important; text-align:center } h3 { font-size:20px!important; text-align:center } h4 { font-size:24px!important; text-align:left } h5 { font-size:20px!important; text-align:left } h6 { font-size:16px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:24px!important } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-header-body h4 a, .es-content-body h4 a, .es-footer-body h4 a { font-size:24px!important } .es-header-body h5 a, .es-content-body h5 a, .es-footer-body h5 a { font-size:20px!important } .es-header-body h6 a, .es-content-body h6 a, .es-footer-body h6 a { font-size:16px!important } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body a { font-size:14px!important } .es-footer-body p, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock a { font-size:12px!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3, .es-m-txt-c h4, .es-m-txt-c h5, .es-m-txt-c h6 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3, .es-m-txt-r h4, .es-m-txt-r h5, .es-m-txt-r h6 { text-align:right!important } .es-m-txt-j, .es-m-txt-j h1, .es-m-txt-j h2, .es-m-txt-j h3, .es-m-txt-j h4, .es-m-txt-j h5, .es-m-txt-j h6 { text-align:justify!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3, .es-m-txt-l h4, .es-m-txt-l h5, .es-m-txt-l h6 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-m-txt-r .rollover:hover .rollover-second, .es-m-txt-c .rollover:hover .rollover-second, .es-m-txt-l .rollover:hover .rollover-second { display:inline!important } .es-m-txt-r .rollover span, .es-m-txt-c .rollover span, .es-m-txt-l .rollover span { line-height:0!important; font-size:0!important } .es-spacer { display:inline-table } a.es-button, button.es-button { font-size:18px!important; line-height:120%!important } a.es-button, button.es-button, .es-button-border { display:inline-block!important } .es-m-fw, .es-m-fw.es-fw, .es-m-fw .es-button { display:block!important } .es-m-il, .es-m-il .es-button, .es-social, .es-social td, .es-menu { display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .adapt-img { width:100%!important; height:auto!important } .es-mobile-hidden, .es-hidden { display:none!important } .es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } .es-social td { padding-bottom:10px } .h-auto { height:auto!important } }\n" +
                "@media screen and (max-width:384px) {.mail-message-content { width:414px!important } }\n" +
                "</style>\n" +
                " </head>\n" +
                " <body class=\"body\" style=\"width:100%;height:100%;padding:0;Margin:0\">\n" +
                "  <div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"en\" style=\"background-color:#FFFFFF\"><!--[if gte mso 9]>\n" +
                "\t\t\t<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "\t\t\t\t<v:fill type=\"tile\" color=\"#ffffff\"></v:fill>\n" +
                "\t\t\t</v:background>\n" +
                "\t\t<![endif]-->\n" +
                "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FFFFFF\">\n" +
                "     <tr>\n" +
                "      <td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table bgcolor=\"#fad939\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:510px\">\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:470px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:510px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" bgcolor=\"#FAD939\" role=\"none\">\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0\">\n" +
                "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr>\n" +
                "                  <td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:510px\">\n" +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;position:relative\"><img class=\"adapt-img\" src=\"https://feloewl.stripocdn.email/content/guids/bannerImgGuid/images/image17086835498802550.png\" alt=\"\" title=\"\" width=\"510\" style=\"display:block;font-size:18px;border:0;outline:none;text-decoration:none\" height=\"324\"></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FAD939;border-radius:0 0 50px 50px;width:510px\" role=\"none\">\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:470px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;font-family:Poppins, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:38px;font-style:normal;font-weight:bold;line-height:46px;color:#5d541d\">Please confirm<br>your email address</h1></td>\n" +
                "                     </tr>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:40px;padding-bottom:40px\"><h3 style=\"Margin:0;font-family:Poppins, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:20px;font-style:normal;font-weight:bold;line-height:24px;color:#5D541D\">Thanks for joining "+firstName+"</h3><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:Poppins, sans-serif;line-height:27px;letter-spacing:0;color:#5D541D;font-size:18px\"><br></p><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:Poppins, sans-serif;line-height:27px;letter-spacing:0;color:#5D541D;font-size:18px\">To finish signing up, please confirm your email address. Your confirmation ensures seamless correspondence and enhanced account security. Thank you for your cooperation!\"</p></td>\n" +
                "                     </tr>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0\"><!--[if mso]><a href=\"https://viewstripo.email\" target=\"_blank\" hidden>\n" +
                "\t<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" esdevVmlButton href=\"https://viewstripo.email\" \n" +
                "                style=\"height:49px; v-text-anchor:middle; width:254px\" arcsize=\"50%\" stroke=\"f\"  fillcolor=\"#8928c6\">\n" +
                "\t\t<w:anchorlock></w:anchorlock>\n" +
                "\t\t<center style='color:#ffffff; font-family:Poppins, sans-serif; font-size:16px; font-weight:400; line-height:16px;  mso-text-raise:1px'>Confirm email address</center>\n" +
                "\t</v:roundrect></a>\n" +
                "<![endif]--><!--[if !mso]><!-- --><span class=\"es-button-border msohide\" style=\"border-style:solid;border-color:#2CB543;background:#8928c6;border-width:0px;display:inline-block;border-radius:30px;width:auto;mso-hide:all\"><a href=\"" + link + "\" class=\"es-button\"  target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none !important;mso-line-height-rule:exactly;color:#FFFFFF;font-size:16px;padding:15px 35px 15px 35px;display:inline-block;background:#8928c6;border-radius:30px;font-family:Poppins, sans-serif;font-weight:normal;font-style:normal;line-height:19px;width:auto;text-align:center;letter-spacing:0;mso-padding-alt:0;mso-border-alt:10px solid #660099;border-color:#8928c6\">Confirm email address</a></span><!--<![endif]--></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"Margin:0;padding-right:20px;padding-left:20px;padding-bottom:40px;padding-top:20px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:470px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:Poppins, sans-serif;line-height:21px;letter-spacing:0;color:#5D541D;font-size:14px\"><br>The link will expire in 15 minutes! &nbsp;</p></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table bgcolor=\"#fad939\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:510px\">\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:470px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table bgcolor=\"#ffffff\" class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#333333;border-radius:50px;width:510px\" role=\"none\">\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"Margin:0;padding-right:20px;padding-left:20px;padding-top:20px;padding-bottom:20px\"><!--[if mso]><table style=\"width:470px\" cellpadding=\"0\" \n" +
                "                        cellspacing=\"0\"><tr><td style=\"width:225px\" valign=\"top\"><![endif]-->\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:225px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:Poppins, sans-serif;line-height:21px;letter-spacing:0;color:#FFFFFF;font-size:14px\">HOUSELAND LTD.</p></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:225px\" valign=\"top\"><![endif]-->\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:225px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"right\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-top:5px;font-size:0\">\n" +
                "                       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-table-not-adapt es-social\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                         <tr>\n" +
                "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:10px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img src=\"https://feloewl.stripocdn.email/content/assets/img/social-icons/circle-white/facebook-circle-white.png\" alt=\"Fb\" title=\"Facebook\" height=\"24\" style=\"display:block;font-size:18px;border:0;outline:none;text-decoration:none\"></a></td>\n" +
                "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:10px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img src=\"https://feloewl.stripocdn.email/content/assets/img/social-icons/circle-white/x-circle-white.png\" alt=\"X\" title=\"X.com\" height=\"24\" style=\"display:block;font-size:18px;border:0;outline:none;text-decoration:none\"></a></td>\n" +
                "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:10px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img src=\"https://feloewl.stripocdn.email/content/assets/img/social-icons/circle-white/instagram-circle-white.png\" alt=\"Ig\" title=\"Instagram\" height=\"24\" style=\"display:block;font-size:18px;border:0;outline:none;text-decoration:none\"></a></td>\n" +
                "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img src=\"https://feloewl.stripocdn.email/content/assets/img/social-icons/circle-white/youtube-circle-white.png\" alt=\"Yt\" title=\"Youtube\" height=\"24\" style=\"display:block;font-size:18px;border:0;outline:none;text-decoration:none\"></a></td>\n" +
                "                         </tr>\n" +
                "                       </table></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table><!--[if mso]></td></tr></table><![endif]--></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n" +
                "         <tr>\n" +
                "          <td class=\"es-info-area\" align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;border-radius:50px;width:510px\" role=\"none\">\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px;padding-top:20px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:470px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" class=\"es-infoblock\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:Poppins, sans-serif;line-height:18px;letter-spacing:0;color:#CCCCCC;font-size:12px\">The expiration link will expire in 15 minutes!</p></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table bgcolor=\"#fad939\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:510px\">\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:470px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table></td>\n" +
                "     </tr>\n" +
                "   </table>\n" +
                "  </br>\n" +
                "  </br>\n" +
                "  </div>\n" +
                " </body>\n" +
                "</html>";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userService.findByEmail(request.getEmail());

            if(user.getIsVerified() && user.getRole().name().equals("CUSTOMER")){

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
            }else{
                throw new InvalidCredentialsException("User is not verified!");

            }

        }catch (BadCredentialsException e){

            throw new InvalidCredentialsException("Invalid email or password");
        }

    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(refreshToken);
        if(userEmail != null){
            var user = userService.findByEmail(userEmail);
            if(jwtService.isTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }
    }

    @Transactional
    public boolean confirmToken(String token) {
        LOGGER.info("Received token for confirmation: {}", token);
        ConfirmationToken confirmationToken = confirmationTokenService .getToken(token);

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        //enable user account
        userService.enableUserAccount(confirmationToken.getUser().getEmail());
        //creating a shopping cart for the user
        shoppingCartService.createShoppingCart(confirmationToken.getUser().getId());
        return true;

    }
}

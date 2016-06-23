<?php
/**
 * Controller is the customized base controller class.
 * All controller classes for this application should extend from this base class.
 */

class BaseController extends CController
{
	/**
	 * @var string the default layout for the controller view. Defaults to '//layouts/column1',
	 * meaning using a single column layout. See 'protected/views/layouts/column1.php'.
	 */
	public $layout='//layouts/column1';
	/**
	 * @var array context menu items. This property will be assigned to {@link CMenu::items}.
	 */
	public $menu=array();
	/**
	 * @var array the breadcrumbs of the current page. The value of this property will
	 * be assigned to {@link CBreadcrumbs::links}. Please refer to {@link CBreadcrumbs::links}
	 * for more details on how to specify this property.
	 */
	public $breadcrumbs=array();

	protected function beforeAction($action){

		#$this->casValidation();
		
		return parent::beforeAction($action);
	}


	public function casValidation()
	{
		$casPath = Yii::getPathOfAlias('ext.CAS');

		Yii::log(" casPath = $casPath");

		// Turn off our amazing library autoload 
		spl_autoload_unregister(array('YiiBase','autoload'));

		// Load the settings from the central config file
		include($casPath . DIRECTORY_SEPARATOR . 'config.php');

		// Load the CAS lib
		include($casPath . DIRECTORY_SEPARATOR . 'CAS.php');

		// Enable debugging
		phpCAS::setDebug();
		// Enable verbose error messages. Disable in production!
		phpCAS::setVerbose(true);

		// Initialize phpCAS
		phpCAS::client(CAS_VERSION_2_0, $cas_host, $cas_port, $cas_context);

		// For production use set the CA certificate that is the issuer of the cert
		// on the CAS server and uncomment the line below
		// phpCAS::setCasServerCACert($cas_server_ca_cert_path);

		// For quick testing you can disable SSL validation of the CAS server.
		// THIS SETTING IS NOT RECOMMENDED FOR PRODUCTION.
		// VALIDATING THE CAS SERVER IS CRUCIAL TO THE SECURITY OF THE CAS PROTOCOL!
		phpCAS::setNoCasServerValidation();

		// force CAS authentication
		phpCAS::forceAuthentication();

		spl_autoload_register(array('YiiBase','autoload'));
	}

}